import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.HashMap;

@Getter @Setter
public class Response {
    private String statusLine;
    private HashMap<String, String> headers;
    private JSONObject body;
    private String errorMessage;

    private final String lineSeparator = "\r\n";

    public Response(String statusLine, HashMap<String, String> headers, JSONObject body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public Response(JSONObject body) {
        this("HTTP/1.1 200",
            new HashMap<>() {{
                put("Content-RequestType", "text/plain");
                put("Connection", "close");
            }},
            body);
    }

    public Response(String errorMessage) {
        this((JSONObject) null);
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(statusLine).append(lineSeparator);
        headers.forEach((key, value) -> builder.append(key).append(": ").append(value).append(lineSeparator));
        builder.append(lineSeparator);
        if (body != null) {
            builder.append(body.toString()).append(lineSeparator);
        } else if (errorMessage != null) {
            builder.append(errorMessage).append(lineSeparator);
        }
        return builder.toString();
    }
}