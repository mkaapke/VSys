package BidiNetcatTCP;

import java.io.IOException;

public class WebClient {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Wrong input!");
            return;
        } else {
            String host = args[0];
        }
    }
}
