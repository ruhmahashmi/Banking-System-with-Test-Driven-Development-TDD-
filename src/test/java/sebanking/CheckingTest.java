package sebanking;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingTest {
    private static final String ID = "12345678";

    @Test
    void checking_account_has_id_when_created() {
        Checking account = new Checking(ID, 0.0);
        assertEquals(ID, account.getId());
    }
}