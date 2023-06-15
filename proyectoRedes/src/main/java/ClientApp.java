import controller.ClientController;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) throws IOException {
        ClientController l = new ClientController("Test", "password");
    }
}
