import org.json.JSONObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class Controller {

    private ConcurrentHashMap<Integer, Task> tasksMap;

    ExecutorService service;

    public Controller(ExecutorService service) {
        this.service = service;
        this.tasksMap = new ConcurrentHashMap<>();
    }

    @ControllerMethod(type=RequestType.POST, name="factorial_task")
    public String factorialController(Request request) {
        FactorialTask task = new FactorialTask(Integer.parseInt(request.getParams().get("number")));
        tasksMap.put(task.getId(), task);
        service.execute(task);
        return new JSONObject().put("id", task.getId()).toString();
    }

    @ControllerMethod(type=RequestType.GET, name="check")
    public String checkController(Request request) {
        Task task;
        if ((task = tasksMap.get(Integer.parseInt(request.getParams().get("id")))) != null) {
            if (task.getStatus() != Status.DONE) {
                return new JSONObject().put("status", task.getStatus().name()).toString();
            } else {
                return task.getJsonResult();
            }
        } else {
            return "Error: invalid ID";
        }
    }
}
