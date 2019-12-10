package RMI.Aufgabe2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class Transmitter {

    public static void main(String[] args) throws IOException, NotBoundException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Receiver receiver = (Receiver) Naming.lookup("//localhost:1099/Receiver");

        String message;
        while((message = in.readLine()) != null) {
            receiver.receive(message);
        }
        in.close();


    }

}
