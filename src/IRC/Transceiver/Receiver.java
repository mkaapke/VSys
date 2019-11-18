package IRC.Transceiver;

import IRC.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receiver implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private Client client;

    public Receiver(Socket socket, Client client) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.client = client;
    }

    private void receiver() throws IOException {
        String message;
        while(!(message = in.readLine()).equals("\u0004")) {
            client.request(message);
        }
        socket.shutdownInput();
    }

    @Override
    public void run() {
        try {
            receiver();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
