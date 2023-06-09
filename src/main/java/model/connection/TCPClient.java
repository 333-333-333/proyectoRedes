package model.connection;

import org.apache.logging.log4j.core.jmx.Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    private final int SERVER_PORT = 33333;
    private String ServerIP;
    private Socket ServerSocket;

    public TCPClient() {
        try {
            this.ServerIP = "localhost";
            this.ServerSocket = new Socket(this.ServerIP, this.SERVER_PORT);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String recieveMessage() throws IOException {
        InputStream inputStream = ServerSocket.getInputStream();
        byte[] buffer = new byte[8192];
        int bytesRead = inputStream.read(buffer);
        return new String(buffer, 0, bytesRead);
    }

    public void printMessage() {
        try {
            System.out.println(recieveMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
