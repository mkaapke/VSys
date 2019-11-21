package IRC;

import IRC.Commands.Command;
import IRC.Commands.Commands;
import IRC.Transceiver.Transceiver;
import Netcat.Actor;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client extends Thread implements Actor {

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
        user = new User(null, null, socket.getRemoteSocketAddress().toString(), this, false);
    }

    public void run() {
        try {
            transceiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Diese Methode verarbeitet die Befehle, die der Client schickt.
     * @throws IOException
     */
    public void request(String message) throws IOException {
        Command command = getParameter(message, getCommand(message));

        if (command.getName().equals("NOT FOUND")) {
            tell(ircServer.getErrors().getMessage(421, null, null, null,
                    wrongCommand(message)), null);
        } else {
            if (command.getParameters().length != command.getSize()) {
                tell(ircServer.getErrors().getMessage(130, null, null, null,
                        command.getName()), null);
            } else {
                if (!user.isRegister()
                        && (!command.getName().equals("NICK") && !command
                        .getName().equals("USER"))) {
                    tell(ircServer.getErrors().getMessage(451, null, null,
                            null, null), null);
                }

                if (command.getName() == "NICK") {
                    String rpl = ircServer.nick(command.getParameters()[1],
                            user);
                    if (rpl.contains("RPL_NICKCHANGE"))
                        ircServer.serverMessages(rpl);
                    else
                        tell(rpl, null);
                }

                if (command.getName() == "USER") {
                    tell(ircServer.addUser(command.getParameters()[1],
                            command.getParameters()[1], client, this), null);
                }

                if (user.isRegister() && command.getName() == "PRIVMSG") {
                    ircServer.sendPrivateMessage(command.getParameters()[1],
                            command.getParameters()[2], user);
                }

                if (user.isRegister() && command.getName() == "NOTICE") {
                    ircServer.notice(command.getParameters()[1],
                            command.getParameters()[2], user);
                }

                if (user.isRegister() && command.getName() == "PING") {
                    tell(ircServer.pong(), null);
                }

                if (user.isRegister() && command.getName() == "PONG") {
                }

                if (user.isRegister() && command.getName() == "QUIT") {
                    ircServer.removeUser(user, command.getParameters()[1]);
                    shutdown();
                }

            }
        }
    }

    public String wrongCommand(String message) {
        String[] split = message.split(" ");
        return split[0];
    }

    public Command getCommand(String message) {
        for (Command c : commands.getCommands()) {
            if (message.startsWith(c.getName())) {
                if (message.length() == c.getName().length()) return c;
                if (message.charAt(c.getName().length()) == ' ') {
                    return c;
                }
            }
        }
        return commands.getCommands().get(0);
    }

    public Command getParameter(String message, Command command) {
        String mess;
        if (command.getSize() == 1)
            return command;
        String[] finalParameters;
        if (command.isNoMessage()) {
            finalParameters = message.split(" ");
            if (command.getName().equals("WHOIS")
                    && finalParameters.length == 1)
                return command;
            command.setParameters(finalParameters);
            return command;
        }
        String[] split = message.split(":");
        if (split.length == 1)
            mess = "";
        else
            mess = split[1];
        String parameters = split[0];
        List<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(parameters.split(" ")));
        list.add(mess);
        finalParameters = new String[list.size()];
        finalParameters = list.toArray(finalParameters);
        command.setParameters(finalParameters);
        return command;
    }

    public boolean checkParameter(String[] parameters) {
        for (String s : parameters) {
            if (s == null)
                return false;
        }
        return true;
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
