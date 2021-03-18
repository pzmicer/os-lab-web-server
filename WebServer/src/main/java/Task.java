enum Status { QUEUED, PROCESSING, DONE }

public abstract class Task implements Runnable {

    private static int ID = 0;

    protected int id;
    protected Status status;

    public abstract String getJsonResult();
    public abstract void solve();

    Task() {
        id = getNextID();
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static int getNextID() {
        return ID++;
    }

    @Override
    public void run() {
        status = Status.PROCESSING;
        solve();
        status = Status.DONE;
    }
}
