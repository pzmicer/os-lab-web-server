import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.concurrent.Callable;

enum Status { QUEUED, PROCESSING, DONE }

public abstract class Task implements Runnable, Callable<Task> {

    private static int ID = 0;
    public static int getNextID() {
        return ID++;
    }

    @Getter
    protected int id;
    @Getter @Setter
    protected Status status;

    Task() {
        id = getNextID();
    }

    public abstract void solve();
    public abstract JSONObject getJsonResult();

    @Override
    public void run() {
        status = Status.PROCESSING;
        solve();
        status = Status.DONE;
    }

    @Override
    public Task call() throws Exception {
        run();
        return this;
    }
}
