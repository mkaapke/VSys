package IRC.TCP_ECHO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private ServerSocket serverSocket;

    public EchoServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        request();
    }

    public void request() throws IOException {
        while(true) {
            Socket socket = serverSocket.accept();
            new Echo(socket, this).start();
        }
    }
}
