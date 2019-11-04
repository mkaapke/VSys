package UniNetcatTCP;

import BidiNetcat.ReaderPrinter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Receiver implements Runnable{

    private Socket socket;
    private ReaderPrinter rp;
    private BufferedReader in;

    public Receiver(Socket socket, ReaderPrinter rp) throws IOException {
        this.socket = socket;
        this.rp = rp;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void receiver() throws IOException {
        String message;
        while(!(message = in.readLine()).equals("\u0004")) {
            rp.tell(message, null);
            //rp.startDaytime();  // Methode für den Daytime-Server
            //rp.startEcho(message); // Methode für den Echo-Server
            /*if(message.equals("zitat")) {
                rp.startZitate(zitate());
            }*/
        }
        socket.shutdownInput();
        in.close();
    }

    public String zitate() throws IOException {
        ArrayList<String> datei = new ArrayList<>();
        String dateiName = "D:\\IntelliJ Projects\\VSys_Praktikum1\\src\\BidiNetcat\\Zitate\\zitate.txt";

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
