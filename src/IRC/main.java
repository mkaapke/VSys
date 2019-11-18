package IRC;

import java.io.IOException;

public class main {
    public static void main(String args[]) throws IOException {
        System.err.println("Server wird gestartet");
        int port = Integer.valueOf(args[0]);
        IRCServer ircServer = new IRCServer(port);
    }
}
