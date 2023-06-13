package model.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HalfManClient {

    private static final String HALFMAN_IP = "localhost";
    private static final int HALFMAN_PORT = 30303;
    private static final int CLIENT_PORT = 33300;

    public static String getClientIP() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ipAddress = inetAddress.getHostAddress();
        return ipAddress;
    }

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

    public static void askRecieptClientIP() throws IOException {
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("1");
        stream.close();
        halfmanSocket.close();
    }

    public static String getServerIP() throws IOException {
        sendClientIP();
        askServerIP();
        return catchServerIP();
    }

    public static void askServerIP() throws IOException {
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("2");
        stream.close();
        halfmanSocket.close();
    }

    public static String catchServerIP() throws IOException {
        ServerSocket localServerSocket = new ServerSocket(CLIENT_PORT);
        Socket localSocket = localServerSocket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());
        localServerSocket.close();
        return streamer.readUTF();
    }

    public static boolean hasLink() throws IOException {
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("5");
        stream.close();
        halfmanSocket.close();

        return catchLinkState();
    }

    public static boolean catchLinkState() throws IOException {
        ServerSocket localServerSocket = new ServerSocket(CLIENT_PORT);
        Socket localSocket = localServerSocket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());
        localServerSocket.close();
        String linkState = streamer.readUTF();
        return linkState.equals("1");
    }

}
