package UniNetcatTCP;

        import java.io.IOException;

public class NetcatTCP {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 2){
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            Reader reader = new Reader(host, port);
            //reader.reader();
            reader.send();


        } else if(args[0].equals("-l")) {
            int port = 5555;

            Receiver receiver = new Receiver(port);
            receiver.receiver();
        } else {
            System.err.println("Usage: Netcat <host> <port>  || Netcat <port>");
        }

    }
}
