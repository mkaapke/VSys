package UniNetcatTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Reader {
    private Transmitter transmitter;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    Reader(String host, int port) throws IOException {
        transmitter = new Transmitter(host, port);
    }

    public void reader() throws IOException {
        String message;
        while((message = in.readLine()) != null) {
            transmitter.tell(message, null);
        }
        transmitter.shutdown();
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

            transmitter.tell(message, null);

            TimeUnit.SECONDS.sleep(5);
        }
    }
}
