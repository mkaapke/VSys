package UniNetcatTCP;

import Netcat.Actor;

import java.io.IOException;

public class Printer implements Actor {
    @Override
    public void tell(String message, Actor sender) throws IOException {
        System.out.println(message);
    }

    @Override
    public void shutdown() {
        System.err.println("Server wurde beendet");
    }
}
