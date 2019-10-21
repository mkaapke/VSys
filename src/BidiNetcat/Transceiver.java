package BidiNetcat;

import Netcat.Actor;
import Netcat.Receiver;
import Netcat.Transmitter;
import Netcat.UDPSocket;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Transceiver implements Actor {

    private Transmitter transmitter;
    private Receiver receiver;
    private UDPSocket udpSocket;
    private Thread thread;

    public Transceiver(String host, int port, ReaderPrinter readerPrinter) throws SocketException, UnknownHostException {
        udpSocket = new UDPSocket(host, port);
        transmitter = new Transmitter(udpSocket);
        receiver = new Receiver(udpSocket, readerPrinter);
        thread = new Thread(receiver);
    }

    public Transceiver(int port, ReaderPrinter readerPrinter) throws SocketException {
        udpSocket = new UDPSocket(port);
        transmitter = new Transmitter(udpSocket);
        receiver = new Receiver(udpSocket, readerPrinter);
        thread = new Thread(receiver);
    }

    void startReceiving() {
        thread.start();
    }

    @Override
    public void tell(String message, Actor sender) throws IOException {
        transmitter.tell(message, sender);
    }

    @Override
    public void shutdown() throws IOException {
        transmitter.shutdown();
    }
}
