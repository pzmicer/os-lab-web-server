import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Controller {

    private final int THREAD_COUNT = 4;

    private LinkedBlockingDeque<Task> tasksQueue;
    private ConcurrentHashMap<Integer, Task> tasksMap;

    private ArrayList<TaskThread> taskThreads;
    private TaskThread tasksThread;

    public Controller() {
        this.tasksQueue = new LinkedBlockingDeque<>();
        this.tasksMap = new ConcurrentHashMap<>();
        this.taskThreads = new ArrayList<>();
        //this.tasksThread = new TaskThread();
        for(int i = 0; i < THREAD_COUNT; i++)
            taskThreads.add(new TaskThread());
    }

    public void startThreads() {
        //tasksThread.start();
        for(TaskThread thread : taskThreads)
            thread.start();
    }

    public String factorialController(Request request) {
        FactorialTask task = new FactorialTask(Integer.parseInt(request.getParams().get("number")));
        tasksQueue.add(task);
        task.setStatus(Status.QUEUED);
        tasksMap.put(task.getId(), task);
        return new JSONObject().put("id", task.getId()).toString();
    }

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

    private class TaskThread extends Thread {
        @Override
        public void run() {
            for(;;) {
                try {
                    Task task = tasksQueue.take();
                    task.setStatus(Status.PROCESSING);
                    task.solve();
                    task.setStatus(Status.DONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
