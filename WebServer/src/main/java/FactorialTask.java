import com.google.common.math.BigIntegerMath;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.math.BigInteger;

@Getter @Setter
public class FactorialTask extends Task {

    private int number;
    private BigInteger result;

    public FactorialTask(int number) {
        super();
        this.number = number;
    }

    @Override
    public JSONObject getJsonResult() {
        return new JSONObject()
                .put("task", "factorial")
                .put("result", result)
                .put("number", number);
    }

    @Override
    public void solve() {
        result = BigIntegerMath.factorial(number);
    }
}