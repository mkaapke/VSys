package Echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class EchoServer {
    private static final int BUFSIZE = 508;
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        // Socket an Port binden
        try (DatagramSocket socket = new DatagramSocket(port)) {

            // Packet zum Empfangen bzw. Senden
            DatagramPacket packet = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
            while(true) {
                //Packet empfangen
                socket.receive(packet);
                System.out.println("Received: " + packet.toString());

                //Packet an Absender zurücksckicken
                socket.send(packet);
            }
        } catch (IOException e) {System.err.println(e);}
    }
}
