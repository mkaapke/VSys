package Netcat;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Transmitter implements Actor {

    private UDPSocket udpSocket;

    public Transmitter(UDPSocket udpSocket) throws UnknownHostException, SocketException {
        this.udpSocket = udpSocket;
    }

    @Override
    public void tell(String message, Actor sender) throws IOException {
        udpSocket.send(message);
    }

    @Override
    public void shutdown() throws IOException {
        udpSocket.send("\u0004");
        System.err.println("Ich kann nicht mehr senden!");
    }
}
