/*package Netcat;

import java.io.IOException;

public class Netcat {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 2){
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            Reader reader = new Reader(host, port);
            //reader.reader();
            reader.send();

        } else if(args.length == 1) {
            int port = Integer.parseInt(args[0]);
            int maxBytes = 1000;

            Receiver receiver = new Receiver(port, maxBytes);
            receiver.receiver();
        } else {
            System.err.println("Usage: Netcat <host> <port>  || Netcat <port>");
        }

    }
}*/
