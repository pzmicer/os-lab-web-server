import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class WebServerTest {

    @Test
    public void IntegrationTest() {
        try {
            /*Thread serverThread = new Thread(() -> {
                WebServer server = new WebServer();
                server.start();
            });
            serverThread.start();*/
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080?controller=factorial_task&number=5"))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(new JSONObject(response.body()).get("id"), 0);
            //serverThread.interrupt();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}