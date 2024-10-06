import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathsTest {

    @Test
    public void testSum() {
        // given
        int term1 = 2;
        int term2 = 3;

        // do
        int sum = term1 + term2;

        // expect
        Assertions.assertEquals(5, sum);
    }
}
