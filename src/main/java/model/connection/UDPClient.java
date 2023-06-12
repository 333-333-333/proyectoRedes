package model.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPClient {

    private final int PORT = 33333;
    private DatagramSocket Socket;

    public UDPClient() throws SocketException {
        Socket = new DatagramSocket(PORT);
    }

    public void recieve(DatagramPacket packet) throws IOException {
        Socket.receive(packet);
    }

}
