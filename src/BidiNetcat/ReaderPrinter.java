package BidiNetcat;

import Netcat.Actor;
import Netcat.Printer;
import Netcat.Reader;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ReaderPrinter implements Actor {

    private Reader reader;
    private Printer printer;
    private Transceiver transceiver;
    private Thread thread;

    public ReaderPrinter(String host, int port) throws SocketException, UnknownHostException {
        this.printer = new Printer();
        this.transceiver = new Transceiver(host, port, this);
        this.reader = new Reader(transceiver);
        thread = new Thread(reader);
    }

    public ReaderPrinter(int port) throws SocketException {
        this.printer = new Printer();
        this.transceiver = new Transceiver(port, this);
        this.reader = new Reader(transceiver);
        thread = new Thread(reader);
    }

    public void startReader() {
        thread.start();
        transceiver.startReceiving();
    }

    @Override
    public void tell(String message, Actor sender) throws IOException {
        printer.tell(message, null);
    }

    @Override
    public void shutdown() throws IOException {
        System.err.println("Ich beende das Senden, kann aber noch empfangen!");
    }
}
