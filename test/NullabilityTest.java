import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NullabilityTest {

    @Test
    public void shouldBeNull() {
        String nullString = null;
        assertNull(nullString);
    }

    @Test
    public void shouldNotBeNull() {
        String apple = "apple";
        assertNotNull(apple);
    }
}
