import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        WebServer server = new WebServer();
        server.start();
        System.out.println("Continue Main");
        System.exit(0);
    }
}