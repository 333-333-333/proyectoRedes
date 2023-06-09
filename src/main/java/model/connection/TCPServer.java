package model.connection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    private boolean IsOpen;
    private final int PORT = 33333;
    private final ServerSocket Socket;
    private Socket ClientSocket;

    public TCPServer() {
        try {
            IsOpen = false;
            this.Socket = new ServerSocket(this.PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openSocket() {
        this.IsOpen = true;
        System.out.println("Waiting for client to connect...");
    }

    public void closeSocket() {
        this.IsOpen = false;
        System.out.println("Connection ended.");
    }

    public void startConnection() {
        try {
            if (!this.IsOpen) {
                openSocket();
            }
            this.ClientSocket = this.Socket.accept();
            System.out.println("Connection started!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void endConnection() {
        try {
            closeSocket();
            this.Socket.close();
            System.out.println("Socket closed.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendString(String s) throws IOException {
        startConnection();
        OutputStream out =  this.ClientSocket.getOutputStream();
        out.write(s.getBytes());
    }

    public ServerSocket getSocket() {
        return this.Socket;
    }

    public Socket getClientSocket() {
        return this.ClientSocket;
    }

}
