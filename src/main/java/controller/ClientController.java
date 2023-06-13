package controller;

import model.connection.HalfManClient;
import model.connection.HalfManServer;
import model.connection.TCPClient;
import model.connection.UDPClient;
import view.ClientGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
public class ClientController {
    private TCPClient TCPClient;
    private UDPClient UDPClient;
    private ClientGUI GUI;
    private String ServerIP;
    private boolean IsConnected;

    public ClientController() throws IOException {
        GUI = new ClientGUI();
        IsConnected = false;
        stablishConnection();
    }

    private void stablishConnection() throws IOException {
        HalfManClient.sendClientIP();
        while(!IsConnected) {
            try {
                IsConnected = HalfManClient.hasLink();
                System.out.println(IsConnected);
                if (IsConnected) {
                    ServerIP = HalfManClient.getServerIP();
                    TCPClient = new TCPClient(ServerIP);
                    UDPClient = new UDPClient();
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Runtime problem (!)");
            }
        }
    }

    public void run() {
        Thread runScreenReceptor = new Thread(() -> {
            while (true) {
                try {
                    byte[] imageBytes = new byte[65536];
                    DatagramPacket imagePacket = new DatagramPacket(imageBytes,
                        imageBytes.length);

                    UDPClient.recieve(imagePacket);
                    byte[] imageData = imagePacket.getData();

                    ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
                    BufferedImage image = ImageIO.read(inputStream);

                    Image scaledImage = image.getScaledInstance(720, 480,
                            Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(scaledImage);
                    GUI.getVideoLabel().setIcon(icon);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread runLoggerReceptor = new Thread(() -> {
            GUI.getLoggerDescription().setText("[Comienza el log]");
            while (true) {
                try {
                    GUI.getLoggerDescription().setText(
                            GUI.getLoggerDescription().getText() +
                            "\n>" +
                            TCPClient.recieveMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        runScreenReceptor.start();
        runLoggerReceptor.start();
    }
}
