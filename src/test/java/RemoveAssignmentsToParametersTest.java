import io.github.YonghoChoi.refactoring.RemoveAssignmentsToParameters;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class RemoveAssignmentsToParametersTest {
    @Test
    public void test() {
        RemoveAssignmentsToParameters instance = new RemoveAssignmentsToParameters();
        int result = instance.discount(10, 10, 10);
        assertTrue(result == instance.discount_step1(10, 10, 10));
        assertTrue(result == instance.discount_step2(10, 10, 10));
        instance.param();
    }
}
