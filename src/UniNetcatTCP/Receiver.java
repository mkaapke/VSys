package UniNetcatTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

class Receiver {


    private Printer printer = new Printer();
    private BufferedReader in;

    Receiver(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    void receiver() throws IOException {
        String message;
        while(!(message = in.readLine()).equals("\u0004")) {
            printer.tell(message, null);
        }
        printer.shutdown();
        in.close();
    }
}
