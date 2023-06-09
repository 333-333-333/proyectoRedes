package controller.logger;

import model.connection.TCPClient;

import java.io.IOException;

public class LoggerClientController {
    private TCPClient Client;

    public LoggerClientController() {
        this.Client = new TCPClient();
    }

    public void run() {
        Thread runLoggerReceptor = new Thread(() -> {
            while (true) {
                this.Client.printMessage();
            }
        });

        runLoggerReceptor.start();
    }
}
