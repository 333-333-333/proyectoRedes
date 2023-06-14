package model.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

/*
As purpose, this class lets client side talk with HalfMan, providing his IP
and getting the state of the link between client and server.
 */

public class HalfManClient {



    // As a suposition, HalfMan IP address must be known.
    private static final String HALFMAN_IP = "localhost";
    // Port for sending to HalfMan.
    private static final int HALFMAN_PORT = 30303;
    // Port for recieving from HalfMan.
    private static final int CLIENT_PORT = 33300;



    // Once gotten the machine IP, it has to arrive to HalfMan.
    public static void sendClientIP() throws IOException {
        askRecieptClientIP();
        String serverIP = getClientIP();
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF(serverIP);
        stream.close();
        halfmanSocket.close();
    }

    // Local machine's IP.
    public static String getClientIP() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress.getHostAddress();
    }

    // Warns HalfMan that the next thing he's going to recieve is client's
    // IP address.
    private static void askRecieptClientIP() throws IOException {
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("1");
        stream.close();
        halfmanSocket.close();
    }

    // Gets the state of the link between client and server (True or false).
    public static boolean hasLink() throws IOException {
        System.out.println("Asking HalfMan link state...");
        askStreamLinkState();
        return catchLinkState();
    }

    // Sends the solicitude to HalfMan to send the link state.
    private static void askStreamLinkState() throws IOException {
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("5");
        stream.close();
        halfmanSocket.close();
    }

    // Cathces the answer from HalfMan about the link state.
    public static boolean catchLinkState() throws IOException {
        ServerSocket localServerSocket = new ServerSocket(CLIENT_PORT);
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
