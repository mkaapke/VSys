package IRC.Transceiver;

import IRC.Client;
import IRC.TCP_ECHO.Echo;
import IRC.TCP_ZITATE.Zitate;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Receiver implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private Client client;
    private Zitate zitate;
    private Echo echo;

    public Receiver(Socket socket, Client client) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.client = client;
    }

    public Receiver(Socket socket, Zitate zitate) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.zitate = zitate;
    }

    public Receiver(Socket socket, Echo echo) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.echo = echo;
    }

    private void receiver() throws IOException {
        String message;
        while(!(message = in.readLine()).equals("\u0004")) {
            client.request(message);
            //zitate.tell(zitate(), null); // Zitate-Server
            //echo.tell(message, null); // Echo-Server
        }
        socket.shutdownInput();
    }

    public String zitate() throws IOException {
        ArrayList<String> datei = new ArrayList<>();
        String dateiName = "D:\\IntelliJ Projects\\VSys_Praktikum1\\src\\IRC\\Transceiver\\zitate.txt";

        File file = new File(dateiName);

        BufferedReader in = new BufferedReader(new FileReader(file));

        String zeile = in.readLine();

        while (zeile != null) {
            datei.add(zeile);
            zeile = in.readLine();
        }
        in.close();

        int x = (int) (Math.random() * ((datei.size() / 2)) * 2);

        String zitat = datei.get(x) + "\n" + datei.get(x + 1);

        return zitat;
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
