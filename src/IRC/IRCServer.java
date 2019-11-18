package IRC;

import IRC.Errors.Errors;
import IRC.Replies.Replies;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class IRCServer {

    private List<User> users = new ArrayList<User>();
    private ServerSocket serverSocket;
    private Errors errors;
    private Replies replies;
    private String host = InetAddress.getLocalHost().toString();

    public IRCServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        replies = new Replies(host);
        errors = new Errors(host);
        request();
    }


    public void request() throws IOException {
        while(true) {
            Socket socket = serverSocket.accept();
            new Client(socket, this).start();
        }
    }


    public String nick(String nick, User user) {
        for (User u : users) {
            if (u.getNick().equals(nick)) {
                return errors.getMessage(433, nick, null, null, null);
            }
        }
        user.setNick(nick);
        users.add(user);
        return null;
    }

    public String addUser(String nick, String username, String client, Client ct) {
        User newUser = new User(nick, username, client, ct);

        users.add(newUser);
        ct.setUser(newUser);
        return replies.getMessage(001, nick, username, client);
    }
}
