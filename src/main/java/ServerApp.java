import model.program.DataStreamer;

import java.awt.*;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerApp {
    public static void main(String[] args) throws SocketException, UnknownHostException, AWTException {
        DataStreamer streamer = new DataStreamer();
        streamer.run();
    }
}
