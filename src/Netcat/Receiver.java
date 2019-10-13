package Netcat;

import java.io.IOException;

public class Receiver {

    private UDPSocket udpSocket;
    private Printer printer = new Printer();
    private int maxBytes = 1024;

    Receiver (int port, int maxBytes) {
        udpSocket = new UDPSocket(port);
        this.maxBytes = maxBytes;
    }

    public void receiver() throws IOException {
        String message;
        while(!(message = udpSocket.receive(maxBytes)).equals("\u0004")) {
            printer.tell(message, null);
        }
    }

}
