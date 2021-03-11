import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MonoRequestHandler implements Runnable {

    Socket client;
    Controller controller;

    public MonoRequestHandler(Socket client, Controller controller) {
        this.client = client;
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream());

            // Start sending our reply, using the HTTP 1.1 protocol
            out.print("HTTP/1.1 200 \r\n"); // Version & status code
            out.print("Content-RequestType: text/plain\r\n"); // The type of data
            out.print("Connection: close\r\n"); // Will close stream
            out.print("\r\n"); // End of headers

            String line;
            ArrayList<String> lines = new ArrayList<>();
            while (in.ready() && (line = in.readLine()) != null) {
                if (line.length() == 0)
                    break;
                lines.add(line);
            }
            //TODO something
            if (lines.size() == 0) {
                return;//continue
            }
            System.out.println(lines.get(0));
            Request request = Request.parse(lines.get(0));
            if (request.getType() == RequestType.GET) {
                out.println(controller.checkController(request));
            } else if (request.getType() == RequestType.POST) {
                switch (request.getParams().get("controller")) {
                    case "factorial_task" -> {
                        out.println(controller.factorialController(request));
                    }
                    default -> out.println("Error 404");
                }
            }
            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
