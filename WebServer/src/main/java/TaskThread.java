import com.google.common.math.BigIntegerMath;

public class TaskThread implements Runnable {

    Task task;

    public TaskThread(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.setStatus(Status.PROCESSING);
        task.setResult(BigIntegerMath.factorial(task.getNumber()));
        task.setStatus(Status.DONE);
    }
}
