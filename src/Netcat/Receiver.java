package Netcat;

import BidiNetcat.ReaderPrinter;

import java.io.IOException;
import java.net.SocketException;

public class Receiver implements Runnable{

    private UDPSocket udpSocket;
    private ReaderPrinter readerPrinter;
    private int maxBytes = 100000;

    public Receiver(UDPSocket udpSocket, ReaderPrinter readerPrinter) throws SocketException {
        this.udpSocket = udpSocket;
        this.readerPrinter = readerPrinter;
    }

    public void receiver() throws IOException {
        String message;
        while(!(message = udpSocket.receive(maxBytes)).equals("\u0004")) {
            readerPrinter.tell(message, null);
        }
        readerPrinter.shutdown();
    }

    @Override
    public void run() {
        try {
            this.receiver();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
