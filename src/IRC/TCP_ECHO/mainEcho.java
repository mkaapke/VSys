package IRC.TCP_ECHO;

import java.io.IOException;

public class mainEcho {
    public static void main(String args[]) throws IOException {
        System.err.println("Server wird gestartet");
        int port = Integer.valueOf(args[0]);
        EchoServer echoServer = new EchoServer(port);
    }
}
