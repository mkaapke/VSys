package DayTime;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DayTimeClient {
    private static final int BUFSIZE = 508;
    public static  void main(String[] args) {
        if (args.length != 2){
            System.err.println("Usage: java DayTimeClient <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        //Socket an anonymen Port binden
        try (DatagramSocket socket = new DatagramSocket()) {

            // leeres Packet an Server senden
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packetout = new DatagramPacket(new byte[1], 1, address, port);
            socket.send(packetout);

            // Antwortpacket empfangen
            DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
            socket.receive(packetIn);
            String received = new String(packetIn.getData(), 0, packetIn.getLength());
            System.out.println("Current time: " + received);
        } catch (Exception e) { System.err.println(e); }
    }
}
