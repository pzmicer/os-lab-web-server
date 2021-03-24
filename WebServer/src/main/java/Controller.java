import org.json.JSONObject;

import java.util.concurrent.*;

public class Controller {

    private final ConcurrentHashMap<Integer, Future<Task>> tasksMap;
    private final ExecutorService service;

    public Controller(ExecutorService taskService) {
        this.service = taskService;
        this.tasksMap = new ConcurrentHashMap<>();
    }

    @ControllerMethod(type=RequestType.POST, name="factorial_task")
    public Response factorialController(Request request) {
        FactorialTask task = new FactorialTask(Integer.parseInt(request.getParams().get("number")));
        tasksMap.put(task.getId(), service.submit((Callable<Task>) task));
        return new Response(new JSONObject().put("id", task.getId()));
    }

    @ControllerMethod(type=RequestType.GET, name="check")
    public Response checkController(Request request) {
        Future<Task> task;
        if ((task = tasksMap.get(Integer.parseInt(request.getParams().get("id")))) != null) {
            if (!task.isDone()) {
                return new Response(new JSONObject().put("status", Status.PROCESSING.name()));
            }
            try {
                return new Response(task.get().getJsonResult());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return new Response("Error: invalid ID");
    }
}
