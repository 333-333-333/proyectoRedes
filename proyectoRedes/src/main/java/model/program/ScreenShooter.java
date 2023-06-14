package model.program;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScreenShooter {



    // Width of the scale.
    private static final int WIDTH= 720;
    // Length of the scale
    private static final int LENGTH = 480;
    // Captures the screen.
    private Robot ScreenShooter;
    // Then dimention it
    private Rectangle Dimention;



    // Constructor.
    public ScreenShooter() throws AWTException{
        ScreenShooter = new Robot();
        Dimention = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }



    // Gets screenshot, then get convert it to bytes.
    public byte[] getframeBytes() throws IOException {
        BufferedImage screenshot = sreenshot();
        BufferedImage resizedScreenshot = dimentionFrame(screenshot);
        return tryCompress(resizedScreenshot);
    }

    // Gets screenshot
    public BufferedImage sreenshot() {
        return ScreenShooter.createScreenCapture(Dimention);
    }

    // Resizes image to a certain dimention.
    private static BufferedImage dimentionFrame(BufferedImage image) {
        BufferedImage resized = new BufferedImage(WIDTH, LENGTH,
                image.TYPE_INT_RGB);
        resized.getGraphics().drawImage(image,
                0, 0,
                WIDTH, LENGTH,
                null);
        return resized;
    }

    // Resizes and scales image to a certain dimention.
    private static BufferedImage dimentionFrame(BufferedImage image, float rate) {
        BufferedImage resized = new BufferedImage(
                (int) (WIDTH * rate), (int) (LENGTH * rate),
                image.TYPE_INT_RGB);
        resized.getGraphics().drawImage(image,
                0, 0,
                (int) (WIDTH * rate),(int) (LENGTH * rate),
                null);
        return resized;
    }

    public static byte[] tryCompress(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


        ImageIO.write(image, "png", outputStream);

        byte[] original = outputStream.toByteArray();
        float size = original.length;

        return (size > 65535) ? compress(image, size) : original;
    }

    public static byte[] compress(BufferedImage image,
                                  float size) throws IOException {
        float compressRate = 1 / (size / 65_000);

        BufferedImage compressedScreenshot = dimentionFrame(image, compressRate);
        BufferedImage resizedScreenshot = dimentionFrame(compressedScreenshot);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedScreenshot, "png", outputStream);

        return outputStream.toByteArray();
    }

}
