package IRC.TCP_ZITATE;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ZitateServer {

    private ServerSocket serverSocket;

    public ZitateServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        request();
    }

    public void request() throws IOException {
        while(true) {
            Socket socket = serverSocket.accept();
            new Zitate(socket, this).start();
        }
    }
}
