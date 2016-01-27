import io.github.YonghoChoi.refactoring.SplitTemporaryVariable;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class SplitTemporaryVariableTest {
    @Test
    public void test() {
        SplitTemporaryVariable instance = new SplitTemporaryVariable();
        double result = instance.getDistanceTravelled(10);
        assertTrue(result == instance.getDistanceTravelled_step1(10));
        assertTrue(result == instance.getDistanceTravelled_step2(10));
    }

}
