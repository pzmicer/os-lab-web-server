import java.util.HashMap;
import java.util.StringTokenizer;


public class Request {

    private RequestType type;
    private HashMap<String, String> params;
    private String body = null;

    public Request() {
        params = new HashMap<>();
    }

    public static Request parse(String line) {
        Request request = new Request();
        StringTokenizer tokenizer = new StringTokenizer(line, " ");
        String strType = tokenizer.nextToken();
        request.type = extractType(strType);
        String url = tokenizer.nextToken();
        tokenizer = new StringTokenizer(url, "/?&=");
        while(tokenizer.hasMoreTokens()) {
            String key = tokenizer.nextToken();
            String value = tokenizer.nextToken();
            request.params.put(key, value);
        }
        return request;
    }

    private static RequestType extractType(String type) {
        return switch (type) {
            case "GET" -> RequestType.GET;
            case "POST" -> RequestType.POST;
            default -> null;
        };
    }

    public RequestType getType() {
        return type;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public String getBody() {
        return body;
    }
}
