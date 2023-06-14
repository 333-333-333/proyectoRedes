package model.connection;

import java.io.IOException;
import java.net.*;

public class UDPServer {



    // Port for sharing datagrams to client.
    private final int PORT = 33333;
    // Datagram that connects to client, for sharing purposes.
    private DatagramSocket Socket;
    // Data of client (UDP level).
    private InetAddress Reciever;
    // Client's IP-
    private String ClientIP;



    // Constructor.
    public UDPServer(String clientIP) throws IOException {
        Socket = new DatagramSocket();
        ClientIP = clientIP;
        Reciever = InetAddress.getByName(ClientIP);
    }



    // Creates a empty datagram packet that's directed to client's machine.
    private DatagramPacket byteDatagram(byte[] data) {
        return new DatagramPacket(data, data.length, Reciever, PORT);
    }

    // Send a byte array to client.
    public void sendBytes(byte[] data) throws IOException {
        sendDatagram(byteDatagram(data));
    }

    // Sends a datagram to client-
    public void sendDatagram(DatagramPacket datagramPacket) throws IOException {
        Socket.send(datagramPacket);
    }

}
