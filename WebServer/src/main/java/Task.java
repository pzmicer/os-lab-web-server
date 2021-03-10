public abstract class Task {

    private static int ID = 0;
    protected int id;
    protected Status status;

    public abstract String getJsonResult();
    public abstract void solve();

    Task() {
        id = getNextID();
    }

    public static int getNextID() {
        return ID++;
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
}
