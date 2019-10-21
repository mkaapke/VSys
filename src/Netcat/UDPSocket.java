package Netcat;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UDPSocket {
    private InetAddress host;
    private int port;
    private DatagramSocket socket;

    public UDPSocket(String host, int port) throws SocketException, UnknownHostException {
        this.host = InetAddress.getByName(host);
        this.port = port;
        this.socket = new DatagramSocket();
    }

    public UDPSocket(int port) throws SocketException {
        this.port = port;
        this.socket = new DatagramSocket(port);
    }

    public void send(String s) throws IOException {
        DatagramPacket packetOut = new DatagramPacket(s.getBytes(), s.length(), host, port);
        socket.send(packetOut);
    }

    public String receive(int maxBytes) throws IOException {
        DatagramPacket packetIn = new DatagramPacket(new byte[maxBytes], maxBytes);
        socket.receive(packetIn);
        if (host == null) {
            host = packetIn.getAddress();
            port = packetIn.getPort();
            //socket.send(packetIn); // Zeile für den EchoClient
            //time(maxBytes, packetIn);
            //zitate(maxBytes, packetIn);
        }

        return /*packetIn.getSocketAddress() + " " + */new String(packetIn.getData(), 0, packetIn.getLength());
    }

    /**
     * Methode um Zitate zu generieren
     * @param maxBytes
     * @param dp
     * @throws IOException
     */
    public void zitate(int maxBytes, DatagramPacket dp) throws IOException {
        ArrayList<String> datei = new ArrayList<>();
        String dateiName = "D:\\IntelliJ Projects\\VSys_Praktikum1\\src\\BidiNetcat\\Zitate\\zitate.txt";

        File file = new File(dateiName);

        BufferedReader in = new BufferedReader(new FileReader(file));

        String zeile = in.readLine();

        while(zeile != null) {
            datei.add(zeile);
            zeile = in.readLine();
        }
        in.close();

        int x = (int)(Math.random() * ((datei.size()/2))*2);

        String zitat = datei.get(x) + "\n" + datei.get(x+1);

        DatagramPacket packetOut = new DatagramPacket(new byte[maxBytes],maxBytes);

        byte[] data = zitat.getBytes();
        packetOut.setData(data);
        packetOut.setLength(data.length);

        packetOut.setSocketAddress(dp.getSocketAddress());

        socket.send(packetOut);
    }

    /**
     * Methode für den DayTimeServer
     * @param maxBytes
     * @param dp
     * @throws IOException
     */
    public void time(int maxBytes, DatagramPacket dp) throws IOException {
        DatagramPacket packetOut = new DatagramPacket(new byte[maxBytes],maxBytes);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());

        packetOut.setData(time.getBytes());
        packetOut.setLength(time.length());

        packetOut.setSocketAddress(dp.getSocketAddress());

        socket.send(packetOut);
    }
}
