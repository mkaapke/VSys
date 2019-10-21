package BidiNetcat.Zitate;

import BidiNetcat.ReaderPrinter;

import java.io.IOException;

public class ZitateClient {
    public static  void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java ZitateClient <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        ReaderPrinter rp = new ReaderPrinter(host, port);
        rp.startReader();
    }
}
