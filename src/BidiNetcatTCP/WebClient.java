package BidiNetcatTCP;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class WebClient {

    public static void main(String[] args) throws IOException {

        if (args.length < 1) return;

        String hostname = args[0];
        int port = 80;
        String datei = args[1];

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println("HEAD " + datei + " HTTP/1.1");
            writer.println("Host: " + hostname);
            writer.println("User-Agent: Simple Http Client");
            writer.println("Accept: text/html");
            writer.println("Accept-Language: en-US");
            writer.println("Connection: close");
            writer.println();

            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
