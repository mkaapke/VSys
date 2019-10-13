package Netcat;

import java.io.IOException;
import java.net.*;

public class UDPSocket {
    private InetAddress host;
    private int port;

    public UDPSocket(String host, int port) throws UnknownHostException {
        this.host = InetAddress.getByName(host);
        this.port = port;
    }

    public UDPSocket(int port) {
        this.port = port;
    }

    public void send(String s) throws IOException {
        DatagramSocket socket = new DatagramSocket();

        DatagramPacket packetOut = new DatagramPacket(s.getBytes(), s.length(), host, port);
        socket.send(packetOut);
        socket.close();
    }

    public String receive(int maxBytes) throws IOException {
        DatagramSocket socket = new DatagramSocket(port);

        DatagramPacket packetIn = new DatagramPacket(new byte[maxBytes], maxBytes);
        socket.receive(packetIn);
        socket.close();
        return packetIn.getSocketAddress() + " " + new String(packetIn.getData(), 0, packetIn.getLength());
    }
}
