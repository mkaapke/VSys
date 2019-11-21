package IRC.TCP_ZITATE;

import IRC.Transceiver.Transceiver;
import Netcat.Actor;

import java.io.IOException;
import java.net.Socket;

public class Zitate extends Thread implements Actor {

    private Socket socket;
    private ZitateServer zitateServer;
    private Transceiver transceiver;

    public Zitate(Socket socket, ZitateServer zitateServer) throws IOException {
        this.socket = socket;
        this.zitateServer = zitateServer;
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
