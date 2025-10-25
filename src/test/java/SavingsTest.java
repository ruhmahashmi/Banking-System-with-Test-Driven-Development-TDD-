import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsTest {
    @Test
    void initial_balance_is_zero() {
        Savings savings = new Savings("12345678", 0.5);
        assertEquals(0.0, savings.getBalance(), 0.0001);
    }

    @Test
    void apr_is_supplied() {
        Savings savings = new Savings("12345678", 0.5);
        assertEquals(0.5, savings.getApr(), 0.0001);
    }
}