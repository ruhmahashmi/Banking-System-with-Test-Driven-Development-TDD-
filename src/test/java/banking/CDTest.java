package banking;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDTest {
    @Test
    void initial_balance_is_supplied() {
        CD cd = new CD("12345678", 0.5, 1500.75);
        assertEquals(1500.75, cd.getBalance(), 0.0001);
    }

    @Test
    void apr_is_supplied() {
        CD cd = new CD("12345678", 0.5, 1500.75);
        assertEquals(0.5, cd.getApr(), 0.0001);
    }
}