package DayTime;

import BidiNetcat.ReaderPrinter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DayTimeServer {
    private static final int BUFSIZE = 508;
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java DayTimeServer <port>");
            return;
        }

        int port = Integer.parseInt(args[0]);

        ReaderPrinter rp = new ReaderPrinter(port);
        rp.startReader();

        /*try(DatagramSocket socket = new DatagramSocket(port)) {
            DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE],BUFSIZE);
            DatagramPacket packetOut = new DatagramPacket(new byte[BUFSIZE],BUFSIZE);

            while(true) {
                socket.receive(packetIn);
                System.out.println("Received time inquiry!");

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String time = sdf.format(new Date());

                packetOut.setData(time.getBytes());
                packetOut.setLength(time.length());

                packetOut.setSocketAddress(packetIn.getSocketAddress());

                socket.send(packetOut);
            }
        } catch (Exception e) { System.err.println(e); }*/
    }
}
