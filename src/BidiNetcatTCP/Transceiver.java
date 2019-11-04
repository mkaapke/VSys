package BidiNetcatTCP;

import BidiNetcat.ReaderPrinter;
import Netcat.Actor;
import UniNetcatTCP.Receiver;
import UniNetcatTCP.Transmitter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transceiver implements Actor {

    private Transmitter transmitter;
    private Receiver receiver;
    private Thread thread;
    private Socket socket;

    public Transceiver(String host, int port, ReaderPrinter rp) throws IOException {
        socket = new Socket(host, port);
        transmitter = new Transmitter(socket);
        receiver = new Receiver(socket, rp);
        thread = new Thread(receiver);
    }

    public Transceiver(int port, ReaderPrinter rp) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        transmitter = new Transmitter(socket);
        receiver = new Receiver(socket, rp);
        thread = new Thread(receiver);
    }

    public void startTransceiver() {
        thread.start();
    }

    /**
     * Methode für den Daytime-Server
     * @throws IOException
     */
    public void startDaytime() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        transmitter.tell(time, null);
        transmitter.shutdown();
    }

    /**
     * Methode für den Echo-Server
     * @param s
     * @throws IOException
     */
    public void startEcho(String s) throws IOException {
        transmitter.tell(s, null);
        transmitter.shutdown();
    }

    /**
     * Methode für den Zitate-Server
     * @param s
     * @throws IOException
     */
    public void startZitate(String s) throws IOException {
        transmitter.tell(s, null);
        transmitter.shutdown();
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
