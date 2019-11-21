package IRC.TCP_ECHO;

import IRC.Transceiver.Transceiver;
import Netcat.Actor;

import java.io.IOException;
import java.net.Socket;

public class Echo extends Thread implements Actor {

    private Socket socket;
    private EchoServer echoServer;
    private Transceiver transceiver;

    public Echo(Socket socket, EchoServer echoServer) throws IOException {
        this.socket = socket;
        this.echoServer = echoServer;
        this.transceiver = new Transceiver(socket, this);
    }



    public void run() {
        try {
            transceiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tell(String message, Actor sender) throws IOException {
        transceiver.tell(message, null);
    }

    @Override
    public void shutdown() throws IOException {
        transceiver.shutdown();
        socket.close();
    }
}
