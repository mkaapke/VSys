package BidiNetcat;

import java.io.IOException;

public class BidiNetcat {
    public static void main(String[] args) throws IOException {
        if (args.length == 2){
            String host = args[0];
            int port = Integer.parseInt(args[1]);

            ReaderPrinter reader = new ReaderPrinter(host, port);
            reader.startReader();

        } else if(args.length == 1) {
            int port = Integer.parseInt(args[0]);

            ReaderPrinter reader = new ReaderPrinter(port);
            reader.startReader();
        } else {
            System.err.println("Usage: Netcat <host> <port>  || Netcat <port>");
        }
    }
}
