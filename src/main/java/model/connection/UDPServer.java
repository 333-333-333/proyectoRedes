package model.connection;

import java.io.IOException;
import java.net.*;

public class UDPServer {

    private final int PORT = 33333;
    private DatagramSocket Socket;
    private InetAddress Reciever;

    public UDPServer() throws SocketException,  UnknownHostException {
        Socket = new DatagramSocket();
        Reciever = InetAddress.getByName("localhost");
    }

    private DatagramPacket byteDatagram(byte[] data) {
        return new DatagramPacket(data, data.length, Reciever, PORT);
    }

    public void sendBytes(byte[] data) throws IOException {
        sendDatagram(byteDatagram(data));
    }

    public void sendDatagram(DatagramPacket datagramPacket) throws IOException {
        Socket.send(datagramPacket);
    }

}
