package RMI.Aufgabe2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ReceiverImpl extends UnicastRemoteObject implements Receiver {

    public ReceiverImpl() throws RemoteException {}

    @Override
    public void receive(String message) throws RemoteException {
        System.out.println(message);
    }
}
