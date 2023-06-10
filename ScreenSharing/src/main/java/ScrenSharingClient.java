import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ScrenSharingClient {
    private static final int PORT = 12345;
    private static final int WIDTH = 720;
    private static final int HEIGHT = 480;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(PORT);

            JFrame frame = new JFrame("Screen Receiver");
            frame.setSize(WIDTH, HEIGHT);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel label = new JLabel();
            frame.getContentPane().add(label);
            frame.setVisible(true);

            while (true) {
                byte[] buffer = new byte[65536];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                byte[] data = packet.getData();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
                BufferedImage screenshot = ImageIO.read(inputStream);

                Image scaledImage = screenshot.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(scaledImage);
                label.setIcon(icon);
                frame.pack();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}