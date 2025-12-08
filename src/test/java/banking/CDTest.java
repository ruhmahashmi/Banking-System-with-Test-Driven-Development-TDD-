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

    @Test
    void cd_passTime_proves_interest_applied() {
        CD cd = new CD("cd1", 2.4, 1000.0);
        cd.passTime(1);
        assertEquals(1002.0, cd.getBalance(), 0.0001);
    }

    @Test
    void cd_line26_boundary_killer() {
        CD cd = new CD("exact", 0.0, 100.0);

        assertEquals(100.0, cd.getBalance(), 1e-12);

        cd.withdraw(100.0);

        assertEquals(0.0, cd.getBalance(), 0.0001);
    }

    @Test
    void withdrawing_exact_balance_should_not_trigger_overdraft_branch() {
        CD cd = new CD("boundary", 0.0, 100.0);

        cd.withdraw(100.0);

        assertEquals(0.0, cd.getBalance(), 0.0001);

        cd.withdraw(1.0);

        assertEquals(0.0, cd.getBalance(), 0.0001);
    }

}

