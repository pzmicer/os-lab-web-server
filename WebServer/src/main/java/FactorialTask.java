import com.google.common.math.BigIntegerMath;
import org.json.JSONObject;

import java.math.BigInteger;

public class FactorialTask extends Task {

    private int number;
    private BigInteger result;

    public FactorialTask(int number) {
        super();
        this.number = number;
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

    @Override
    public String getJsonResult() {
        return new JSONObject()
                .put("result", result)
                .put("number", number).toString();
    }

    @Override
    public void solve() {
        result = BigIntegerMath.factorial(number);
    }
}