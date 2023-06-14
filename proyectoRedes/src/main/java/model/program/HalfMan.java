package model.program;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class HalfMan {



    // Both booleans are used for getting the link state between both.
    private boolean ClientConnected, ServerConnected;
    // Both client and server IP.
    private String ClientIP, ServerIP;
    // Local receptor.
    private ServerSocket Socket;
    // HalfMan's server port.
    private final int PORT = 30303;
    // HalfMan's user port, client side.
    private final int CLIENT_PORT = 33300;
    // HalfMan's user port, server side.
    private final int SERVER_PORT = 30033;



    // Constructor.
    public HalfMan() throws IOException {
        Socket = new ServerSocket(PORT);
    }



    // Receives server IP.
    public void connectServer() throws IOException {
        System.out.println("Getting server IP...");

        Socket localSocket = Socket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());
        ServerIP = streamer.readUTF();
        streamer.close();

        System.out.println("Done! (" + ServerIP + ")");
        ServerConnected = true;
    }

    // Receives client IP.
    public void connectClient() throws IOException {
        System.out.println("Getting client IP...");

        Socket localSocket = Socket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());
        ClientIP = streamer.readUTF();
        streamer.close();

        System.out.println("Done! (" + ClientIP + ")");
        ClientConnected = true;
    }

    // Once gotten server IP, tries to send it to client.
    public void sendToClientServerIP() throws IOException {
        System.out.println("Sending server IP to client...");

        Socket localSocket = new Socket(ClientIP, CLIENT_PORT);
        DataOutputStream streamer = new DataOutputStream(
                localSocket.getOutputStream());
        streamer.writeUTF(ServerIP);
        streamer.close();

        System.out.println("Sent!");
    }

    // Once gotten client IP, tries to send it to client.
    public void sendToServerClientIP() throws IOException {
        System.out.println("Sending client IP to server...");

        Socket localSocket = new Socket(ServerIP, SERVER_PORT);
        DataOutputStream streamer = new DataOutputStream(
                localSocket.getOutputStream());
        streamer.writeUTF(ClientIP);
        streamer.close();

        System.out.println("Sent!");
    }

    // Gets link state (It's the client and server connected?) ti ckuebt.
    public void getClientLinkStateClient() throws IOException {
        System.out.println("Sending link state to client...");

        Socket localSocket = new Socket(ClientIP, CLIENT_PORT);
        DataOutputStream streamer = new DataOutputStream(
                localSocket.getOutputStream());

        String state = (ClientConnected && ServerConnected) ? "1" : "0";
        streamer.writeUTF(state);
        streamer.close();

        System.out.println("Sent!");
    }

    // Sends link state to server.
    public void getServerLinkStateClient() throws IOException {
        System.out.println("Sending link state to server...");

        Socket localSocket = new Socket(ServerIP, SERVER_PORT);
        DataOutputStream streamer = new DataOutputStream(
                localSocket.getOutputStream());

        String state = (ClientConnected && ServerConnected) ? "1" : "0";
        streamer.writeUTF(state);
        streamer.close();

        System.out.println("Sent!");
    }

    // Listens the petitions for both server and client.
    public void listen() throws Exception {
        printLinkState();

        Socket localSocket = Socket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());

        switch (streamer.readUTF()) {
            case "1" -> connectClient();
            case "2" -> sendToClientServerIP();

            case "3" -> connectServer();
            case "4" -> sendToServerClientIP();

            case "5" -> getClientLinkStateClient();
            case "6" -> getServerLinkStateClient();
        }

        sleep(1000);
    }

    // Prints link state, and sepparate connections state for both client and
    // server.
    private void printLinkState() {
        System.out.println("Link state : "
                + (ClientConnected && ServerConnected) );
        System.out.println("Client : " + ClientConnected);
        System.out.println("Server : " + ServerConnected);
    }

}
