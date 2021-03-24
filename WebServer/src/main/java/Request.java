import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.StringTokenizer;

@Getter @Setter
public class Request {

    private RequestType type;
    private HashMap<String, String> params;
    private JSONObject body;

    public Request(JSONObject body) {
        params = new HashMap<>();
        this.body = body;
    }

    public static Request parse(String firstLine) {
        Request request = new Request(null);
        StringTokenizer tokenizer = new StringTokenizer(firstLine, " ");
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
}