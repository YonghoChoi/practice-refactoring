import io.github.YonghoChoi.refactoring.ReplaceTempWithQuery;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class ReplaceTempWithQueryTest {
    @Test
    public void test() {
        ReplaceTempWithQuery instance = new ReplaceTempWithQuery();
        double result = instance.getPrice();
        assertTrue(result == instance.getPrice_step1());
        assertTrue(result == instance.getPrice_step2());
        assertTrue(result == instance.getPrice_step3());
        assertTrue(result == instance.getPrice_step4());
    }
}
