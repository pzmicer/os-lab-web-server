import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private Socket clientSocket;
    BufferedReader in;
    PrintWriter out;

    public Client() throws IOException {
        clientSocket = new Socket("localhost", 8080);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    public String sendPOST(int number) throws IOException {
        String request = "POST /?controller=factorial_task&number=" + number  + "\r\n";
        out.print(request);
        out.flush();
        String line;
        ArrayList<String> lines = new ArrayList<>();
        while (in.ready() && (line = in.readLine()) != null) {
            if (line.length() == 0)
                break;
            lines.add(line);
        }
        return lines.get(0);
    }

    public String sendGET(int id) throws IOException {
        String requestURL = "GET ?controller=check&id=" + id + "\n";
        out.write(requestURL);
        out.flush();
        return in.readLine();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            String result = client.sendPOST(5);
            JSONObject jsonObject = new JSONObject(result);
            System.out.println("id == 0 : " + jsonObject.get("id").equals("0"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
