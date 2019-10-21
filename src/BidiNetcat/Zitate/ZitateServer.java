package BidiNetcat.Zitate;

import BidiNetcat.ReaderPrinter;

public class ZitateServer {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java ZitateServer <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        ReaderPrinter rp = new ReaderPrinter(port);
        rp.startReader();
    }
}
