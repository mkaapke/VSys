package Echo;

import BidiNetcat.ReaderPrinter;


public class EchoClient {
    private static final int BUFSIZE = 508;
    public static  void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: java EchoClient <host> <port> <message>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);


        ReaderPrinter rp = new ReaderPrinter(host, port);
        rp.startReader();

        //Socket an anonymen Port binden
        /*try (DatagramSocket socket = new DatagramSocket()) {

            // leeres Packet an Server senden
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packetout = new DatagramPacket(message.getBytes(), message.length(), address, port);
            socket.send(packetout);

            // Antwortpacket empfangen
            DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
            socket.receive(packetIn);
            String received = new String(packetIn.getData(), 0, packetIn.getLength());
            System.out.println("Echo: " + received);
        } catch (Exception e) { System.err.println(e); }*/
    }
}
