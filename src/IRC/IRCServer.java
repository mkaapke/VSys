package IRC;

import IRC.Errors.Errors;
import IRC.Replies.Replies;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class IRCServer {

    private List<User> users = new ArrayList<User>();
    private List<Channel> channels = new ArrayList<Channel>();
    private List<User> userPerChannel = new ArrayList<>();
    private Map<String, List<User>> channelUser = new HashMap<>();
    private ServerSocket serverSocket;
    private Errors errors;
    private Replies replies;
    private String host = InetAddress.getLocalHost().toString();
    private String created; // Timestamp

    public IRCServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        created = df.format(date);
        replies = new Replies(host, created);
        errors = new Errors(host);
        Channel max = new Channel("#Test", "nice Channel", users);
        channelUser.put(max.getName(), users);
        channelUser.put("#max", null);
        channelUser.put("#Test2", null);
        request();
    }


    @SuppressWarnings("InfiniteLoopStatement")

    private void request() throws IOException {
        while(true) {
            Socket socket = serverSocket.accept();
            new Client(socket, this).start();
        }
    }


    String addUser(String nick, String username, String fullname, Client client) {
        User newUser = new User(nick, username, fullname, client, true);
        for (User u : users) {
            if (u.equals(newUser) && u.isRegister()) //ERR_ALREADYREGISTRED
                return errors.getMessage(462, u.getNick(), username, fullname, null);
            if (u.equals(newUser)) {
                u.setFullname(username);
                u.setRegister(true);
                u.setNick(nick);
                return replies.getMessage(001, nick, fullname, null) + "\n"
                        + replies.getMessage(002, null, null, host) + "\n"
                        + replies.getMessage(003, null, null, null) + "\n"
                        + replies.getMessage(004, null, null, null) + "\n";
            }
        }
        users.add(newUser);
        client.setUser(newUser);
        return replies.getMessage(001, nick, fullname, null) + "\n" //RPL_WELCOME
                + replies.getMessage(002, null, null, host) + "\n" //RPL_YOURHOST
                + replies.getMessage(003, null, null, null) + "\n" //RPL_CREATED
                + replies.getMessage(004, null, null, null) + "\n"; //RPL_MYINFO
    }

    String nick(String nick, User sender) {
        if (nick.length() == 0) { //ERR_NONICKNAMEGIVEN
            return errors.getMessage(431, null, null, null, null);
        }
        if (existNick(nick)) { //ERR_NICKNAMEINUSE
            return errors.getMessage(433, nick, null, null, null);
        }
        for (User u : users) { //RPL_NICKCHANGE
            if (u.equals(sender) && u.isRegister()) {
                sender.setNick(nick);
                return replies.getMessage(999, sender.getAddress(), nick, null);
            }
        }
        sender.setNick(nick);
        users.add(sender);
        return host + ":" + nick + " is available";
    }

    boolean sendPrivateMessage(String nick, String message, User sender) throws IOException {
        if (message.length() == 0) { //ERR_NOTEXTTOSEND
            sender.sendMessage(errors.getMessage(412, sender.getNick(), null, null, null));
            return false;
        }
        if (nick.length() == 0) { //ERR_NORECIPIENT
            sender.sendMessage(errors.getMessage(411, sender.getNick(), null, null, null));
            return false;
        }
        String head = ":" + sender.getNick() + "!" + sender.getAddress() + " PRIVMSG";
        for (User u : users) {
            if (u.getNick().equals(nick)) {
                u.sendMessage(head + " :" + message);
                return true;
            }
        }



        if(channelUser.containsKey(nick)) {
            for (User u : channelUser.get(nick)) {
                u.sendMessage(head + " " + nick + " :" + message);
            }
            return true;
        }
        if (!channelUser.containsKey(nick)) {
            sender.sendMessage(errors.getMessage(404, nick, null, null, null));  // ERR_CANNOTSENDTOCHAN
            return false;
        }


        sender.sendMessage(errors.getMessage(401, nick, null, null, null)); //ERR_NOSUCHNICK
        return false;
    }

    void notice(String nick, String message, User sender) throws IOException {
        String head = ":" + sender.getNick() + "!" + sender.getAddress() + " NOTICE";
        for (User u : users) {
            if (u.getNick().equals(nick)) {
                u.sendMessage(head + " :" + message);
            }
        }

        for (Channel c : channels) {
            List<User> channelUser = new ArrayList<>(c.getUsers());
            if (c.getName().equals(nick) && channelUser.contains(sender)) {
                for (User u : channelUser) {
                    u.sendMessage(head + " " + c.getName() + " :" + message);
                }
            }
        }
    }

    public boolean existNick(String nick) {
        for (User u : users) {
            if (u.getNick().equals(nick))
                return true;
        }
        return false;
    }

    String join(String channelName, User sender) {

        for (Channel c : channels) {
            if(!(c.getName().equals(channelName))) {
                return errors.getMessage(403, channelName, null, null, null); // ERR_NOSUCHCHANNEL
            }
        }
        userPerChannel.add(sender);
        channelUser.put(channelName, userPerChannel);

        return replies.getMessage(353, sender.getNick(), channelName, null) + "\n"  // RPL_NAMEREPLY
               + replies.getMessage(366, sender.getNick(), channelName, null);  // RPL_ENDOFNAMES

    }

    void channelMessages(String channel, User sender) {

    }

    public void serverMessages(String message) throws IOException {
        for (User u : users) {
            u.sendMessage(host + ":" + message);
        }
    }

    String pong() {
        return host + " PONG";
    }

    public Replies getReplies() {
        return replies;
    }

    public Errors getErrors() {
        return errors;
    }

    void removeUser(User user, String message) throws IOException {
        user.getClientThread().interrupt();
        users.remove(user);
        serverMessages(message);
    }

    boolean leaveChannel(User user, String channel) throws IOException {
        if(!(channelUser.get(channel).contains(user))) {
            user.sendMessage(errors.getMessage(442, user.getNick(), null, null, null));
            return false;
        }
        if(!(channelUser.containsKey(channel))) {
            user.sendMessage(errors.getMessage(403, user.getNick(), null, null, null));
            return false;
        }

        return channelUser.get(channel).remove(user);
    }

    String topic(String channelName, User sender) {
        for (Channel c : channels) {
            if (c.getName().equals(channelName)) {
                return c.getTopic() == null ? replies.getMessage(331, channelName, null, null) : replies.getMessage(332, channelName, c.getTopic(), null);
            }
        }

        return errors.getMessage(442, sender.getNick(), null, null, null);

    }

    boolean setTopic(String channelName, String messsage, User sender) throws IOException {
        for (Channel c : channels) {
            if (c.getName().equals(channelName) && c.getUsers().contains(sender)) {
                if (messsage.equals(":")) {
                    c.setTopic(null);
                    return true;
                } else {
                    c.setTopic(messsage.substring(1));
                    return true;
                }
            }
        }
        sender.sendMessage(errors.getMessage(442, sender.getNick(), null, null, null));
        return false;
    }

    String whoIs(String nick) {
        String ret = "";
        for (User u : users) {
            if (u.getNick().equals(nick)) {
                ret+= replies.getMessage(311, nick, u.getAddress() , u.getFullname()) + "\n"; //RPL_WHOISUSER
                ret+= replies.getMessage(312, nick, "MAX-Server", "Version 1.0") + "\n"; //RPL_WHOISSERVER
                ret+= replies.getMessage(318, nick, null , null) + "\n"; //RPL_ENDOFWHOIS
                return ret;
            }
        }
        return errors.getMessage(401, nick, null, null, null); //ERR_NOSUCHNICK
    }

}
