package BidiNetcat;

import BidiNetcatTCP.Transceiver;
import Netcat.Actor;
import Netcat.Printer;
import UniNetcatTCP.Reader;

import java.io.IOException;

public class ReaderPrinter implements Actor {

    private Reader reader;
    private Printer printer;
    private Transceiver transceiver;
    private Thread thread;

    public ReaderPrinter(String host, int port) throws IOException {
        this.printer = new Printer();
        this.transceiver = new Transceiver(host, port, this);
        this.reader = new Reader(transceiver);
        thread = new Thread(reader);
    }

    public ReaderPrinter(int port) throws IOException {
        this.printer = new Printer();
        this.transceiver = new Transceiver(port, this);
        this.reader = new Reader(transceiver);
        thread = new Thread(reader);
    }

    public void startReader() {
        thread.start();
        transceiver.startTransceiver();
    }

    /**
     * Methode für den Daytime-Server
     * @throws IOException
     */
    public void startDaytime() throws IOException {
        transceiver.startDaytime();
    }

    /**
     * Methode für den Echo-Server
     * @param s
     * @throws IOException
     */
    public void startEcho(String s) throws IOException {
        transceiver.startEcho(s);
    }

    /**
     * Methode für den Zitate-Server
     * @param s
     * @throws IOException
     */
    public void startZitate(String s) throws IOException {
        transceiver.startZitate(s);
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
