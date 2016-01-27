import io.github.YonghoChoi.refactoring.IntroduceExplaningVariable;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class IntroduceExplaningVariableTest {
    @Test
    public void test() {
        IntroduceExplaningVariable instance = new IntroduceExplaningVariable();
        double result = instance.price();

        assertTrue(result == instance.price_use_variable_step1());
        assertTrue(result == instance.price_use_variable_step2());
        assertTrue(result == instance.price_use_variable_step3());
        assertTrue(result == instance.price_use_variable_step4());
        assertTrue(result == instance.price_extract_method_use_step1());
        assertTrue(result == instance.price_extract_method_use_step2());
    }
}
