package model.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/*
(Insert description for HalfMan Server)
 */

public class HalfManServer {



    // HalfMan's IP, its static.
    private static final String HALFMAN_IP = "localhost";
    // For sending to HalfMan.
    private static final int HALFMAN_PORT = 30303;
    // For recieving from HalfMan.
    private static final int SERVER_PORT = 30033;



    // Gets machine IP.
    public static String getServerIP() throws UnknownHostException {
        System.out.println("Getting server IP...");
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ipAddress = inetAddress.getHostAddress();
        System.out.println("Done! (" + ipAddress + ")");
        return ipAddress;
    }

    // Sends local machine's IP to HalfMan.
    public static void sendServerIP() throws IOException {
        askRecieptServerIP();
        String serverIP = getServerIP();
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF(serverIP);
        stream.close();
        halfmanSocket.close();
    }

    // Tells Halfman to recieve IP.
    public static void askRecieptServerIP() throws IOException {
        System.out.println("Asking to HalfMan to recieve IP address...");
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("3");
        stream.close();
        halfmanSocket.close();
    }

    // Gets client IP, if there's a link in HalfMan.
    public static String getClientIP() throws IOException {
        sendServerIP();
        askClientIP();
        return catchClientIP();
    }

    // Asks HalfMan to send client's IP.
    public static void askClientIP() throws IOException {
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("4");
        stream.close();
        halfmanSocket.close();
        System.out.println("Done!");
    }

    // Receives client's IP address.
    public static String catchClientIP() throws IOException {
        ServerSocket localServerSocket = new ServerSocket(SERVER_PORT);
        Socket localSocket = localServerSocket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());
        return streamer.readUTF();
    }

    // Returns the link state in HalfMan.
    public static boolean hasLink() throws IOException {
        System.out.println("Asking HalfMan link state...");
        askStreamLinkState();
        return catchLinkState();
    }

    // Asks HalfMan to send the link state.
    private static void askStreamLinkState() throws IOException {
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("6");
        stream.close();
        halfmanSocket.close();
    }

    // Receives link state from HalfMan-
    public static boolean catchLinkState() throws IOException {
        ServerSocket localServerSocket = new ServerSocket(SERVER_PORT);
        Socket localSocket = localServerSocket.accept();

        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());
        localServerSocket.close();
        String linkState = streamer.readUTF();

        boolean hasLink = linkState.equals("1");
        System.out.println("Link state catched!  (" + hasLink + ")");
        return hasLink;
    }

}
