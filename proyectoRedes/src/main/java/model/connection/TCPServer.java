package model.connection;

import java.io.IOException;
import java.net.Socket;

public class TCPServer {



    // Port for sending packages.
    private final int PORT = 33333;
    // Client IP-
    private String ClientIP;
    // For connection purposes, it's required a connection to client.
    private Socket ClientSocket;



    // Constructor.
    public TCPServer(String clientIP) throws IOException {
        System.out.println("Creating TCP server...");
        ClientIP = clientIP;
        ClientSocket = new Socket(ClientIP, PORT);
        System.out.println("TCP server created!");
    }



    // Getter for the client's socket.
    public Socket getClientSocket() {
        return this.ClientSocket;
    }

}