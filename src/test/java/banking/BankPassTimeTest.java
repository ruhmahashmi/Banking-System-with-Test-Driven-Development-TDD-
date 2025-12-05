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
        assertEquals(1001.67, account.getBalance(), 0.01);
        // Monthly interest = 1000 * (0.02 / 12) = 1.6666... → new balance ≈ 1001.67
    }

    @Test
    void savings_below_1000_after_interest_gets_25_fee() {
        bank.createSavings("11111111", 4.0);
        bank.deposit("11111111", 300.00);

        bank.passTime(1);

        Account acc = bank.getAccount("11111111");
        // 300 * 0.04/12 = +1.00 → 301 → still <1000 → $25 fee
        assertEquals(276.00, acc.getBalance(), 0.01);
    }
}