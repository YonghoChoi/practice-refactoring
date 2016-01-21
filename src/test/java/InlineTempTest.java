import io.github.YonghoChoi.refactoring.InlineTemp;
import org.junit.Test;

import static org.junit.Assert.*;

public class InlineTempTest {
    @Test
    public void test() {
        InlineTemp.setBasePrice(2);
        assertFalse(InlineTemp.moreThan1000Price());

        InlineTemp.setBasePrice(1001);
        assertTrue(InlineTemp.moreThan1000Price());
    }
}
