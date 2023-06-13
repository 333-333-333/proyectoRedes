package model.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class HalfManServer {

    private static final String HALFMAN_IP = "localhost";
    private static final int HALFMAN_PORT = 30303;
    private static final int SERVER_PORT = 30033;

    public static String getServerIP() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ipAddress = inetAddress.getHostAddress();
        return ipAddress;
    }

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

    public static void askRecieptServerIP() throws IOException {
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("3");
        stream.close();
        halfmanSocket.close();
    }

    public static String getClientIP() throws IOException {
        sendServerIP();
        askClientIP();
        return catchClientIP();
    }

    public static void askClientIP() throws IOException {
        Socket halfmanSocket = new Socket(HALFMAN_IP, HALFMAN_PORT);
        DataOutputStream stream = new DataOutputStream(
                halfmanSocket.getOutputStream());
        stream.writeUTF("4");
        stream.close();
        halfmanSocket.close();
    }

    public static String catchClientIP() throws IOException {
        ServerSocket localServerSocket = new ServerSocket(SERVER_PORT);
        Socket localSocket = localServerSocket.accept();
        DataInputStream streamer = new DataInputStream(
                localSocket.getInputStream());
        return streamer.readUTF();
    }


}
