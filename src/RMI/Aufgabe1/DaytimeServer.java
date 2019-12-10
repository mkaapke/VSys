package RMI.Aufgabe1;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DaytimeServer {
    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
        DaytimeImpl daytime = new DaytimeImpl();

        Registry registry = LocateRegistry.createRegistry(1099);
        Naming.bind("Daytime", daytime);

        System.out.println("Server wurde gestartet");

    }
}
