package IRC;

import IRC.Commands.Command;
import IRC.Commands.Commands;
import IRC.Transceiver.Transceiver;
import Netcat.Actor;

import java.io.IOException;
import java.net.Socket;

public class Client implements Actor {

    private IRCServer ircServer;
    private Socket socket;
    private Transceiver transceiver;
    private Commands commands = new Commands();
    private User user;
    private String client;

    public Client(Socket socket, IRCServer ircServer) throws IOException {
        this.socket = socket;
        this.ircServer = ircServer;
        this.transceiver = new Transceiver(socket, this);
        this.client = socket.getRemoteSocketAddress().toString();
        user = new User(null, null, socket.getRemoteSocketAddress().toString(), this);
    }

    public void start() throws IOException {
        transceiver.start();
    }

    /**
     * Diese Methode verarbeitet die Befehle, die der Client schickt.
     * @throws IOException
     */
    public void request(String message) throws IOException {
        Command command = getCommand(message);
        if(command.getName() == ("NICK")) {
            tell(ircServer.nick(command.getName(), user), null);
        }


        if(command.getName() == ("USER")) {
            String[] parameters = getParameters(message);
            tell(ircServer.addUser(parameters[1], parameters[1], client, this), null);
        }
    }

    private String[] getParameters(String message) {
        String[] split;
        split = message.split(" ");
        return split;
    }

    private Command getCommand(String message) {
        String[] split = getParameters(message);
        System.out.println(split[0] + " " + split[1]);
        for (Command c : commands.getCommands()) {
            if (split[0].startsWith(c.getName())) {
                if (split[0].length() == c.getName().length())
                    return c;
            }
        }
        return commands.getCommands().get(0);
    }

    @Override
    public void tell(String message, Actor sender) throws IOException {
        transceiver.tell(message, null);
    }

    @Override
    public void shutdown() throws IOException {
        transceiver.shutdown();
        socket.close();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
