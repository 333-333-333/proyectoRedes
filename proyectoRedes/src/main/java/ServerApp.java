import model.program.DataStreamer;

import java.awt.*;
import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException,
            AWTException,
            InterruptedException {
        DataStreamer streamer = new DataStreamer();
        streamer.run();
    }
}
