package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankPassTimeTest {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void passing_one_month_on_empty_bank_does_nothing() {
        bank.passTime(1);
        assertEquals(0, bank.getNumberOfAccounts());
    }
}