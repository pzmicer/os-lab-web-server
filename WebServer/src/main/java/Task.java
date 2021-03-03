import java.math.BigInteger;

enum Status {QUEUED, PROCESSING, DONE }

public class Task {

    private Status status;
    private BigInteger result;
    private int number;

    private static int ID = 0;

    public static int getID() {
        return ID++;
    }

    public Task(int number) {
        this.number = number;
        this.status = Status.QUEUED;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigInteger getResult() {
        return result;
    }

    public void setResult(BigInteger result) {
        this.result = result;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
