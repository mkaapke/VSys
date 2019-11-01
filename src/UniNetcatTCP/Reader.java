package UniNetcatTCP;

import BidiNetcatTCP.Transceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Reader implements Runnable{

    private Transceiver transceiver;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public Reader(Transceiver transceiver) throws IOException {
        this.transceiver = transceiver;
    }

    public void reader() throws IOException {
        String message;
        while((message = in.readLine()) != null) {
            transceiver.tell(message, null);
        }
        transceiver.shutdown();
        in.close();
    }

    /**
     * Methode zur Messdaten-Aufgabe
     * @throws IOException
     * @throws InterruptedException
     */
    public void send() throws IOException, InterruptedException {

        Random zufall = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        while(true) {

            double random = zufall.nextDouble()*100;
            String time = sdf.format(new Date());

            String message = "";
            message = time + " " + random;

            transceiver.tell(message, null);

            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Override
    public void run() {
        try {
            reader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
