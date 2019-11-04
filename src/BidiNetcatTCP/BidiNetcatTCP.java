package BidiNetcatTCP;

import BidiNetcat.ReaderPrinter;

import java.io.IOException;

public class BidiNetcatTCP {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Wrong input!");
            return;
        }

        if (args[0].equals("-l")) {
            int port = Integer.valueOf(args[1]);
            ReaderPrinter rp = new ReaderPrinter(port);
            rp.startReader();
        } else {
            String host = args[0];
            int port = Integer.valueOf(args[1]);
            ReaderPrinter rp = new ReaderPrinter(host, port);
            rp.startReader();
        }

    }

}
