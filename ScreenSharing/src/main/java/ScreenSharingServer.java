import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ScreenSharingServer {
    private static final int PORT = 12345;
    private static final int WIDTH= 720;
    private static final int LENGTH = 480;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress receiverAddress = InetAddress.getByName("localhost");

            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            while (true) {
                BufferedImage screenshot = robot.createScreenCapture(screenRect);
                byte[] data = tryCompress(screenshot);

                if(data.length < 65536) {
                    DatagramPacket packet = new DatagramPacket(data, data.length, receiverAddress, PORT);
                    socket.send(packet);
                }

                Thread.sleep(72);
            }

        } catch (AWTException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static byte[] tryCompress(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        BufferedImage resizedImage = new BufferedImage(WIDTH, LENGTH,
                image.TYPE_INT_RGB);
        resizedImage.getGraphics().drawImage(image,
                0, 0,
                WIDTH, LENGTH,
                null);

        ImageIO.write(resizedImage, "png", outputStream);
        byte[] original = outputStream.toByteArray();
        float size = original.length;
        return (size > 65535) ? compress(image, size) : original;
    }

    public static byte[] compress(BufferedImage image,
                                  float size) throws IOException {
        float compressRate = 1 / (size / 65_000);

        BufferedImage compressedScreenshot = new BufferedImage(
                (int) (WIDTH * compressRate), (int) (LENGTH * compressRate),
                image.TYPE_INT_RGB);
        compressedScreenshot.getGraphics().drawImage(image,
                0, 0,
                (int) (WIDTH * compressRate), (int) (LENGTH * compressRate),
                null);

        BufferedImage resizedScreenshot = new BufferedImage(WIDTH, LENGTH,
                compressedScreenshot.TYPE_INT_RGB);
        resizedScreenshot.getGraphics().drawImage(compressedScreenshot,
                0, 0,
                WIDTH, LENGTH,
                null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedScreenshot, "png", outputStream);
        return outputStream.toByteArray();
    }


}