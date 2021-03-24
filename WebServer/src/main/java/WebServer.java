import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    private final Controller controller;
    private final ExecutorService serviceRequests;
    private final ExecutorService serviceTasks;

    public WebServer() {
        this.serviceRequests = Executors.newFixedThreadPool(2);
        this.serviceTasks = Executors.newFixedThreadPool(2);
        this.controller = new Controller(serviceTasks);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("Server started.");
            while(!server.isClosed()) {
                Socket client = server.accept();
                serviceRequests.execute(new MonoRequestHandler(client, controller));
            }
        } catch (IOException e) {
            if (!(e instanceof SocketException))
                e.printStackTrace();
        } finally {
            serviceRequests.shutdown();
            serviceTasks.shutdown();
        }
    }
}