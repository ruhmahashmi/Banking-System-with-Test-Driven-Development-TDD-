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

    }

    @Test
    void savings_below_1000_after_interest_gets_25_fee() {
        bank.createSavings("11111111", 4.0);
        bank.deposit("11111111", 300.00);

        bank.passTime(1);

        Account acc = bank.getAccount("11111111");
        assertEquals(276.00, acc.getBalance(), 0.01);
    }

    @Test
    void cd_account_cannot_be_withdrawn_before_12_months() {
        bank.createCD("99999999", 1.0, 2000.0);
        bank.passTime(11);

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            bank.withdraw("99999999", 1000);
        });
        assertEquals("CD is not mature yet", thrown.getMessage());
    }

    @Test
    void cd_account_can_be_withdrawn_after_12_months_and_is_removed_on_full_withdrawal() {
        bank.createCD("88888888", 1.0, 2000.0);
        bank.passTime(12);

        Account acc = bank.getAccount("88888888");
        double finalBalance = acc.getBalance();

        bank.withdraw("88888888", finalBalance);

        assertFalse(bank.accountExists("88888888"));
    }

    @Test
    void savings_exactly_1000_after_interest_avoids_fee() {
        Bank bank = new Bank();
        bank.createSavings("sav1000", 1.2);
        bank.deposit("sav1000", 999.00);

        bank.passTime(1);

        assertTrue(bank.getAccount("sav1000").getBalance() > 999.0);
    }

    @Test
    void savings_passTime_increments_months_each_iteration() {
        Savings s = new Savings("sav1", 1.0);
        s.deposit(2000.0);

        s.passTime(3);

        assertEquals(3, s.getMonthsPassed());
    }



}