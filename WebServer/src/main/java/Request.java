import java.util.HashMap;
import java.util.StringTokenizer;

enum Type { GET, POST }

public class Request {
    public Type type;
    public HashMap<String, String> params;

    public Request() {
        params = new HashMap<>();
    }

    public static Request parse(String line) {
        Request request = new Request();
        StringTokenizer tokenizer = new StringTokenizer(line, " ");
        String strType = tokenizer.nextToken();
        if (strType.equals("GET")) {
            request.type = Type.GET;
        } else if (strType.equals("POST")) {
            request.type = Type.POST;
        }
        String url = tokenizer.nextToken();
        tokenizer = new StringTokenizer(url, "/?&=");
        while(tokenizer.hasMoreTokens()) {
            String key = tokenizer.nextToken();
            String value = tokenizer.nextToken();
            request.params.put(key, value);
        }
        return request;
    }
}
