package UniNetcatTCP;

import Netcat.Actor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Transmitter implements Actor {

    private PrintWriter printWriter;
    private Socket socket;

    public Transmitter(Socket socket) throws IOException {
        this.socket = socket;
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void tell(String message, Actor sender) throws IOException {
        printWriter.println(/*socket.getLocalAddress().toString() + " " + */ message);
    }

    @Override
    public void shutdown() throws IOException {
        printWriter.println("\u0004");
        socket.shutdownOutput();
    }
}
