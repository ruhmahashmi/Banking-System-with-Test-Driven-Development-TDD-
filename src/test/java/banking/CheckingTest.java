package banking;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingTest {
    private static final String ID = "12345678";

    @Test
    void checking_account_has_id_when_created() {
        Checking account = new Checking(ID, 0.0);
        assertEquals(ID, account.getId());
    }

    @Test
    void checking_passTime_one_month_applies_interest_and_increments_months() {
        Checking checking = new Checking("11111111", 12.0); // 12% APR
        checking.deposit(1200.0);

        checking.passTime(1);

        assertEquals(1212.0, checking.getBalance(), 0.01);
        assertEquals(1, checking.getMonthsPassed());
    }

    @Test
    void checking_passTime_multiple_months_runs_loop_correct_number_of_times() {
        Checking checking = new Checking("22222222", 12.0);
        checking.deposit(1200.0);

        checking.passTime(2);

        assertEquals(1224.12, checking.getBalance(), 0.02);
        assertEquals(2, checking.getMonthsPassed());
    }

}