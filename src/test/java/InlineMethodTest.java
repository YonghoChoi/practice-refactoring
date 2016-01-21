import io.github.YonghoChoi.refactoring.InlineMethod;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class InlineMethodTest {
    @Test
    public void test() {
        InlineMethod.set_numberOfLateDeliveries(2);
        assertTrue(InlineMethod.getRating() == 1);

        InlineMethod.set_numberOfLateDeliveries(6);
        assertTrue(InlineMethod.getRating() == 2);
    }
}