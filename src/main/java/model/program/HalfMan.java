package model.program;

import org.apache.logging.log4j.core.jmx.Server;

import java.net.ServerSocket;

public class HalfMan {

    private boolean ClientConnected, ServerConnected;
    private String IPClient, IPServer;
    private ServerSocket Socket;

    public void disconnectServer() {
        ServerConnected = false;
        IPServer = null;
    }

    public void connectServer() {
        ServerConnected = true;
    }

    public void disconnectClient() {
        ClientConnected = false;
        IPClient = null;
    }

    public void connectClient() {
        ClientConnected = true;
    }

}
