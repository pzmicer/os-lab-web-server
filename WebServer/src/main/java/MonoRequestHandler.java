import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

public class MonoRequestHandler implements Runnable {

    private final Socket client;
    private final Controller controller;

    public MonoRequestHandler(Socket client, Controller controller) {
        this.client = client;
        this.controller = controller;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream())) {

            String line;
            ArrayList<String> lines = new ArrayList<>();
            while ((line = in.readLine()) != null) {
                if (line.length() == 0)
                    break;
                lines.add(line);
            }
            //TODO int contentLength = new StringTokenizer(lines.get(lines.size()-1), " ")
            if (lines.size() == 0 || lines.get(0) == null) {
                throw new Exception();
            }
            System.out.println(lines.get(0));
            Request request = Request.parse(lines.get(0));
            out.println(findController(request));
            out.flush();
        } catch (IOException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception ignored) { }
        finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Response findController(Request request) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = controller.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ControllerMethod.class)) {
                var annotation = method.getAnnotation(ControllerMethod.class);
                if (annotation.name().equals(request.getParams().get("controller")) &&
                    annotation.type() == request.getType()) {
                    method.setAccessible(true);
                    return (Response) method.invoke(controller, request);
                }
            }
        }
        return new Response("Error 404");
    }
}