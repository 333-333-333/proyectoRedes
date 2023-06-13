package model.program;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HalfMan {

    private boolean ClientConnected, ServerConnected;
    private String ClientIP, ServerIP;
    private ServerSocket Socket;
    private final int PORT = 30303;
    private final int CLIENT_PORT = 33300;
    private final int SERVER_PORT = 30033;

    public HalfMan() throws IOException {
        Socket = new ServerSocket(PORT);
    }

    public void connectServer() throws IOException {
        System.out.println("Getting server IP...");
        Socket localSocket = Socket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());
        ServerIP = streamer.readUTF();
        ServerConnected = true;
        streamer.close();
    }

    public void connectClient() throws IOException {
        System.out.println("Getting client IP...");
        Socket localSocket = Socket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());
        ClientIP = streamer.readUTF();
        ClientConnected = true;
        System.out.println("Done! (" + ClientIP + ")");
        streamer.close();
    }

    public void sendToClientServerIP() throws IOException {
        System.out.println("Sending server IP to client...");
        Socket localSocket = new Socket(ClientIP, CLIENT_PORT);
        DataOutputStream streamer = new DataOutputStream(
                localSocket.getOutputStream());
        streamer.writeUTF(ServerIP);

        streamer.close();
    }

    public void sendToServerClientIP() throws IOException {
        Socket localSocket = new Socket(ServerIP, SERVER_PORT);
        DataOutputStream streamer = new DataOutputStream(
                localSocket.getOutputStream());
        streamer.writeUTF(ClientIP);

        streamer.close();
    }

    public void getLinkStateClient() throws IOException {
        Socket localSocket = new Socket(ClientIP, CLIENT_PORT);
        DataOutputStream streamer = new DataOutputStream(
                localSocket.getOutputStream());

        String state = (ClientConnected && ServerConnected) ? "1" : "0";
        streamer.writeUTF(state);

        streamer.close();
    }

    public void listen() throws Exception {
        System.out.println("Client:" + ClientConnected);
        System.out.println("Server:" + ServerConnected);

        Socket localSocket = Socket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());

        switch (streamer.readUTF()) {
            case "1":
                connectClient();
                break;
            case "2":
                try {
                    sendToClientServerIP();
                } catch (Exception e) {
                    System.out.println("Client or server not connected");
                }
                break;
            case "3":
                connectServer();
                break;
            case "4":
                try {
                    sendToServerClientIP();
                } catch (Exception e) {
                    System.out.println("Client or server not connected.");
                }
                break;
            case "5":
                getLinkStateClient();
                break;
        }
    }

}
