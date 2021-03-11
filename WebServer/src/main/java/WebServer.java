import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    private Controller controller;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private ServerSocket server;
    private Thread consoleThread;

    public WebServer() {
        this.controller = new Controller();
    }

    public void start() {
        try {
            server = new ServerSocket(8080);
            controller.startThreads();
            System.out.println("Server started.");
            consoleThread = new Thread() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(System.in);
                    String serverCommand;
                    while(scanner.hasNextLine()) {
                        serverCommand = scanner.nextLine();
                        if (serverCommand.equals("quit")) {
                            System.out.println("Server closed.");
                            //System.exit(0);
                            WebServer.this.stop();
                            break;
                        }
                    }
                }
            };
            consoleThread.start();
            while(!server.isClosed()) { //for (;;) {
                Socket client = server.accept();
                executorService.execute(new MonoRequestHandler(client, controller));
                //new MonoRequestHandler(client, controller).run();
            }
            executorService.shutdown();
        } catch (IOException e) {
            if (!(e instanceof SocketException))
                e.printStackTrace();
        }
    }

    //doesn't work. ASK
    public void stop() {
        try {
            controller.stopThreads();
            server.close();
            executorService.shutdown();
        } catch (IOException ignored) {

        }
    }
}