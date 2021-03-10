import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Controller {

    private final int THREAD_COUNT = 4;

    private ConcurrentLinkedQueue<Task> tasksQueue;
    private ConcurrentHashMap<Integer, Task> tasksMap;

    //multithreading is ready (i hope) but not turned on
    private ArrayList<TaskThread> taskThreads;
    private TaskThread tasksThread;

    public Controller() {
        this.tasksQueue = new ConcurrentLinkedQueue<>();
        this.tasksMap = new ConcurrentHashMap<>();
        this.taskThreads = new ArrayList<>();
        this.tasksThread = new TaskThread();
        /*for(int i = 0; i < THREAD_COUNT; i++)
            taskThreads.add(new TaskThread());*/
    }

    public void startThreads() {
        tasksThread.start();
        /*for(TaskThread thread : taskThreads)
            thread.start();*/
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
            return "Error: No such id found";
        }
    }

    private class TaskThread extends Thread {
        @Override
        public void run() {
            for(;;) {
                if (!tasksQueue.isEmpty()) {
                    Task task = tasksQueue.poll();
                    task.setStatus(Status.PROCESSING);
                    task.solve();
                    task.setStatus(Status.DONE);
                } else {
                   //TODO wait()
                }
            }
        }
    }
}
