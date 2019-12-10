package RMI.Aufgabe2;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        ReceiverImpl receiver = new ReceiverImpl();

        Registry registry = LocateRegistry.createRegistry(1099);
        Naming.bind("Receiver", receiver);

        System.out.println("Server wurde gestartet");
    }
}
