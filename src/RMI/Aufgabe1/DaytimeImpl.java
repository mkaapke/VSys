package RMI.Aufgabe1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaytimeImpl extends UnicastRemoteObject implements Daytime {

    public DaytimeImpl() throws RemoteException {}

    @Override
    public String getDaytime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

        return sdf.format(date);
    }
}
