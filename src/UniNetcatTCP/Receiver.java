package UniNetcatTCP;

import BidiNetcat.ReaderPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receiver implements Runnable{

    private Socket socket;
    private ReaderPrinter rp;
    private BufferedReader in;

    public Receiver(Socket socket, ReaderPrinter rp) throws IOException {
        this.socket = socket;
        this.rp = rp;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    void receiver() throws IOException {
        String message;
        while(!(message = in.readLine()).equals("\u0004")) {
            rp.tell(message, null);
        }
        socket.shutdownInput();
        in.close();
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
