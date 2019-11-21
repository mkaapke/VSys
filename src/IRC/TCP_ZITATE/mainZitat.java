package IRC.TCP_ZITATE;

import java.io.IOException;

public class mainZitat {
    public static void main(String args[]) throws IOException {
        System.err.println("Server wird gestartet");
        int port = Integer.valueOf(args[0]);
        ZitateServer zitateServer = new ZitateServer(port);
    }
}
