import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanTest {

    @Test
    public void shouldBeTrue() {
        boolean value = true;
        assertTrue(value);
    }

    @Test
    public void shouldBeFalse() {
        boolean value = false;
        Assertions.assertFalse(value);
    }
}
