import io.github.YonghoChoi.refactoring.ReplaceMethodWithMethodObject;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ReplaceMethodWithMethodObjectTest {
    @Test
    public void test() {
        ReplaceMethodWithMethodObject instance = new ReplaceMethodWithMethodObject();
        int result = instance.gamma(10, 10, 10);
        assertTrue(result == instance.gamma_delegate(10, 10, 10));
    }
}
