package IRC;

import IRC.Errors.Errors;
import IRC.Replies.Replies;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IRCServer {

    private List<User> users = new ArrayList<User>();
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
        request();
    }


    public void request() throws IOException {
        while(true) {
            Socket socket = serverSocket.accept();
            new Client(socket, this).start();
        }
    }

    /**
     * Einen neuen User adden
     */
    public String addUser(String nick, String username, String fullname, Client client) throws IOException {
        User newUser = new User(nick, username, fullname, client, true); // der neue User
        for (User u : users) {
            if (u.equals(newUser) && u.isRegister())
                return errors.getMessage(462, u.getNick(), username, fullname, null); // User bereits vorhanden
            if (u.equals(newUser)) { // User hat Nick bereits reserviert, ist aber noch nicht registriert
                u.setFullname(username);
                u.setRegister(true);
                u.setNick(nick);
                return replies.getMessage(001, nick, fullname, null) + "\n"
                        + replies.getMessage(003, null, null, null) + "\n"
                        + replies.getMessage(004, null, null, null) + "\n";
            }
        }
        // User ist noch gar nicht vorhanden
        users.add(newUser);
        client.setUser(newUser);
        return replies.getMessage(001, nick, fullname, null) + "\n"
                + replies.getMessage(003, null, null, null) + "\n"
                + replies.getMessage(004, null, null, null) + "\n";
    }

    public String nick(String nick, User sender) throws IOException {
        if (nick.length() == 0) {
            return errors.getMessage(431, null, null, null, null);
        }
        if (existNick(nick)) {
            return errors.getMessage(433, nick, null, null, null); // Nick bereits im Raum vorhanden
        }
        for (User u : users) { // User durchsuchen, ob der Sender bereits im Raum ist
            if (u.equals(sender) && u.isRegister()) { // User will seinen Nicknamen �ndern und ist bereits registriert
                sender.setNick(nick);
                return replies.getMessage(999, sender.getAddress(), nick, null);
            }
			/*if (u.equals(sender) && !u.isRegister()) { // User will seinen Nicknamen �ndern und ist nicht registriert
				sender.setNick(nick);
				return replies.getMessage(999, nick, null, sender.getAddress(), null, null, null, null);
			}*/
        }
        sender.setNick(nick); // Sender ist noch nicht im Raum und wird angelegt
        users.add(sender);
        return host + ":" + nick + " is available";
    }

    public boolean sendPrivateMessage(String nick, String message, User sender) throws IOException {
        if (message.length() == 0) {
            sender.sendMessage(errors.getMessage(412, sender.getNick(), null, null, null));
            return false;
        }
        if (nick.length() == 0) {
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
        sender.sendMessage(errors.getMessage(401, sender.getNick(), null, null, null));
        return false;
    }

    public void notice(String nick, String message, User sender) throws IOException {
        String head = ":" + sender.getNick() + "!" + sender.getAddress() + " NOTICE";
        for (User u : users) {
            if (u.getNick().equals(nick)) {
                u.sendMessage(head + " :" + message);
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

    public void serverMessages(String message) throws IOException {
        for (User u : users) {
            u.sendMessage(host + ":" + message);
        }
    }

    public String pong() {
        return host + " PONG";
    }

    public Replies getReplies() {
        return replies;
    }

    public Errors getErrors() {
        return errors;
    }

    public void removeUser(User user, String message) throws IOException {
        user.getClientThread().interrupt();
        users.remove(user);
        serverMessages(message);
    }
}
