package RMI.Aufgabe1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class DaytimeClient {
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        Daytime daytime = (Daytime) Naming.lookup("//localhost:1099/Daytime");
        String s = daytime.getDaytime();

        System.out.println("Current time: " + s);
    }
}
