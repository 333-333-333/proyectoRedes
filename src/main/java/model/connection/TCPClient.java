package model.connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TCPClient {
    private int SERVER_PORT = 33333;
    private String ServerIP;
    private Socket ServerSocket;

    public TCPClient() throws IOException {
            ServerIP = "localhost";
            ServerSocket = new Socket(ServerIP, SERVER_PORT);
    }

    public TCPClient(String serverIP) throws IOException {
        System.out.println("Creating TCP Client...");
        ServerIP = serverIP;
        ServerSocket = new Socket(ServerIP, SERVER_PORT);
        System.out.println("TCP Client created!");
    }

    public String recieveMessage() throws IOException {
        InputStream inputStream = ServerSocket.getInputStream();
        byte[] buffer = new byte[8192];
        int bytesRead = inputStream.read(buffer);
        return new String(buffer, 0, bytesRead);
    }

    // Just for debugging.
    public void printMessage() {
        try {
            System.out.println(recieveMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
