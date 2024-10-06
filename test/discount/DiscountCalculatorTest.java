package discount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiscountCalculatorTest {

    DiscountCalculator discountCalculator = new DiscountCalculator();

    @Test
    public void shouldGiveNoDiscountForValue999() {
        // Подготовка
        int buySum = 999;
        int expectedSum = 999;

        // Исполнение
        int resultSum = discountCalculator.sumAfterDiscount(buySum);

        // Проверка
        Assertions.assertEquals(expectedSum, resultSum);
    }

    @Test
    public void shouldGiveNoDiscountForValue1() {
    }

    @Test
    public void shouldGiveNoDiscountForValue333() {
    }

    @Test
    public void shouldGive2PercentDiscountForValue1000() {
    }

    @Test
    public void shouldGive2PercentDiscountForValue2000() {
    }
}