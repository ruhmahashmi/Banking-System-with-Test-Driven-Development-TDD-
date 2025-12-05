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

    @Test
    void savings_account_earns_monthly_interest() {
        bank.createSavings("12345678", 2.0);        // 2% APR
        bank.deposit("12345678", 1000.00);

        bank.passTime(1);

        Account account = bank.getAccount("12345678");
        assertEquals(1000.1667, account.getBalance(), 0.01);
        // Monthly interest = 1000 * (0.02 / 12) = 1.6666... → new balance ≈ 1001.67
    }
}