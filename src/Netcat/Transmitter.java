package Netcat;

import java.io.IOException;
import java.net.UnknownHostException;

public class Transmitter implements Actor {

    private UDPSocket udpSocket;

    Transmitter(String host, int port) throws UnknownHostException {
        udpSocket = new UDPSocket(host, port);
    }

    @Override
    public void tell(String message, Actor sender) throws IOException {
        udpSocket.send(message);
    }

    @Override
    public void shutdown() throws IOException {
        udpSocket.send("\u0004");
    }
}
