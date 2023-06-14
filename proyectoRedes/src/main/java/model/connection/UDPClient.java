package model.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPClient {



    // Port for recieving the frames.
    private final int PORT = 33333;
    // Socket for recieving datagrams.
    private DatagramSocket Socket;



    // Constructor.
    public UDPClient() throws SocketException {
        System.out.println("Creating UDP Client...");
        Socket = new DatagramSocket(PORT);
        System.out.println("UDP Client created!");
    }



    // Recieves a datagram-
    public void recieve(DatagramPacket packet) throws IOException {
        Socket.receive(packet);
    }

}
