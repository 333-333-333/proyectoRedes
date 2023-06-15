import model.program.DataStreamer;

import java.awt.*;
import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) throws IOException,
            AWTException,
            InterruptedException {
        String sessionName = "Test";
        String password = "password";
        DataStreamer streamer = new DataStreamer(sessionName, password);
        streamer.run();
    }
}
