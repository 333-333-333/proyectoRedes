import controller.ClientController;

import java.net.SocketException;

public class ClientApp {
    public static void main(String[] args) throws SocketException {
        ClientController l = new ClientController();
        l.run();
    }
}
