package IRC.Transceiver;

import IRC.Client;
import Netcat.Actor;

import java.io.IOException;
import java.net.Socket;

public class Transceiver implements Actor {
    private Client client;
    private Receiver receiver;
    private Transmitter transmitter;
    private Socket socket;
    private Thread t1;


    public Transceiver (Socket socket, Client client) throws IOException {
        this.client = client;
        this.socket = socket;
        transmitter = new Transmitter(socket);
        receiver = (new Receiver(socket, client));
        t1 = new Thread(receiver);
    }

    public void start() throws IOException {
        t1.start();
    }

    @Override
    public void tell(String message, Actor sender) throws IOException {
        transmitter.tell(message, sender);
    }

    @Override
    public void shutdown() throws IOException {
        transmitter.tell("\u0004", null);

    }

}
