import com.google.common.util.concurrent.FluentFuture;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebServer {

    private Controller controller;
    private ExecutorService executorService = Executors.newFixedThreadPool(4);
    private ServerSocket server;

    public WebServer() {
        this.controller = new Controller(executorService);
    }

    public void start() {
        try {
            server = new ServerSocket(8080);
            System.out.println("Server started.");
            while(!server.isClosed()) {
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
}