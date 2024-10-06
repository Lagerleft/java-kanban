package cookie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FortuneControllerTest {

    private static FortuneCookieFactory factory;

    @BeforeEach
    public void beforeEach() {
        final FortuneConfig config = new FortuneConfig(true);
        factory = new FortuneCookieFactory(
                config,
                singletonList("positive"),
                singletonList("negative")
        );
    }

    @Test
    public void shouldIncrementCountByOneAfterOneCookieBaked() {
        factory.bakeFortuneCookie();
        assertEquals(1, factory.getCookiesBaked());
    }

    @Test
    public void shouldIncrementCountByTwoAfterTwoCookiesBaked() {
        factory.bakeFortuneCookie();
        factory.bakeFortuneCookie();
        assertEquals(2, factory.getCookiesBaked());
    }

    @Test
    public void shouldSetCounterToZeroAfterResetCookieCreatedCall() {
        factory.bakeFortuneCookie();
        assertEquals(1, factory.getCookiesBaked());
        factory.resetCookiesCreated();
        assertEquals(0, factory.getCookiesBaked());
    }
}
