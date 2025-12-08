package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void initial_no_accounts() {
        assertEquals(0, bank.getNumberOfAccounts());
    }

    @Test
    void add_one_account() {
        Account account = new Checking("12345678", 0.5);
        bank.addAccount(account);
        assertEquals(1, bank.getNumberOfAccounts());
    }

    @Test
    void add_two_accounts() {
        Account account1 = new Checking("12345678", 0.5);
        Account account2 = new Savings("87654321", 0.5);
        bank.addAccount(account1);
        bank.addAccount(account2);
        assertEquals(2, bank.getNumberOfAccounts());
    }

    @Test
    void retrieve_account_by_id() {
        Account account = new Checking("12345678", 0.5);
        bank.addAccount(account);
        assertSame(account, bank.getAccount("12345678"));
    }

    @Test
    void deposit_by_id_to_correct_account() {
        Account account1 = new Checking("12345678", 0.5);
        Account account2 = new Savings("87654321", 0.5);
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.deposit("12345678", 100.5);
        assertEquals(100.5, account1.getBalance(), 0.0001);
        assertEquals(0.0, account2.getBalance(), 0.0001);
    }

    @Test
    void withdraw_by_id_from_correct_account() {
        Account account1 = new Checking("12345678", 0.5);
        Account account2 = new Savings("87654321", 0.5);
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.deposit("12345678", 100.5);
        bank.deposit("87654321", 200.75);
        bank.withdraw("12345678", 50.25);
        assertEquals(50.25, account1.getBalance(), 0.0001);
        assertEquals(200.75, account2.getBalance(), 0.0001);
    }

    @Test
    void deposit_twice_by_id() {
        Account account = new Checking("12345678", 0.5);
        bank.addAccount(account);
        bank.deposit("12345678", 100.5);
        bank.deposit("12345678", 100.5);
        assertEquals(201.0, account.getBalance(), 0.0001);
    }

    @Test
    void withdraw_twice_by_id() {
        Account account = new Checking("12345678", 0.5);
        bank.addAccount(account);
        bank.deposit("12345678", 300.75);
        bank.withdraw("12345678", 100.25);
        bank.withdraw("12345678", 100.25);
        assertEquals(100.25, account.getBalance(), 0.0001);
    }


    @Test
    void withdrawing_full_cd_balance_removes_account() {
        Bank bank = new Bank();
        bank.createCD("33333333", 2.0, 2000.0);

        bank.passTime(12);
        bank.withdraw("33333333", 2000.0);

        assertNull(bank.getAccount("33333333"));
    }

    @Test
    void withdrawingExactZeroPointZeroOneFromMatureCDDoesNotRemoveAccount() {
        Bank bank = new Bank();
        bank.createCD("cd1", 0.05, 100.0);
        bank.passTime(12);
        bank.withdraw("cd1", 99.99);
        assertTrue(bank.accountExists("cd1"));
        assertEquals(0.01, bank.getAccount("cd1").getBalance(), 0.001);
    }

    @Test
    void cdWithdrawLeavingMoreThanZeroPointZeroOneKeepsAccount() {
        bank.createCD("cd_keep", 0.0, 1.0);
        bank.passTime(12);
        bank.withdraw("cd_keep", 0.50);
        assertTrue(bank.accountExists("cd_keep"));
    }

    @Test
    void cdWithdrawLeavingLessThanZeroPointZeroOneRemovesAccount() {
        Bank bank = new Bank();
        bank.createCD("cd_remove", 0.0, 0.02);
        bank.passTime(12);
        bank.withdraw("cd_remove", 0.02);
        assertFalse(bank.accountExists("cd_remove"));
    }

    @Test
    void transferExactBalanceSucceeds() {
        Bank bank = new Bank();
        bank.createChecking("from", 0.02);
        bank.createSavings("to", 0.03);
        bank.deposit("from", 100.0);
        bank.transfer("from", "to", 100.0);
        assertEquals(0.0, bank.getAccount("from").getBalance(), 0.001);
        assertEquals(100.0, bank.getAccount("to").getBalance(), 0.001);
    }

    @Test
    void transferSlightlyMoreThanBalanceFails() {
        Bank bank = new Bank();
        bank.createChecking("from2", 0.02);
        bank.createSavings("to2", 0.03);
        bank.deposit("from2", 100.0);
        bank.transfer("from2", "to2", 100.001);
        assertEquals(100.0, bank.getAccount("from2").getBalance(), 0.001);
    }

    @Test
    void getFormattedSummaryWithMixedAccounts() {
        Bank bank = new Bank();
        bank.createChecking("chk1", 0.02);
        bank.createSavings("sav1", 0.03);
        bank.createCD("cd1", 0.05, 1000.0);
        bank.deposit("chk1", 500.0);
        String summary = bank.getFormattedSummary();
        assertTrue(summary.contains("Checking") && summary.contains("chk1"));
        assertTrue(summary.contains("Savings") && summary.contains("sav1"));
        assertTrue(summary.contains("CD") && summary.contains("cd1"));
    }

    @Test
    void getNumberOfAccountsAfterCreates() {
        Bank bank = new Bank();
        bank.createChecking("chk2", 0.02);
        bank.createSavings("sav2", 0.03);
        assertEquals(2, bank.getNumberOfAccounts());
    }


    @Test
    void getAccountsReturnsAllAccounts() {
        Bank bank = new Bank();
        bank.createChecking("chk2", 0.02);
        bank.createSavings("sav2", 0.03);
        Collection<Account> accounts = bank.getAccounts();
        assertEquals(2, accounts.size());
        assertTrue(bank.getNumberOfAccounts() == accounts.size());
    }

    @Test
    void passTimeZeroMonthsReturnsTrue() {
        Bank bank = new Bank();
        bank.createSavings("sav3", 0.03);
        boolean result = bank.passTime(0);
        assertTrue(result);
        assertEquals(0, bank.getAccount("sav3").getMonthsPassed());
    }

    @Test
    void transfer_from_immature_cd_fails_silently() {
        Bank bank = new Bank();
        bank.createCD("11111111", 1.0, 1000);
        bank.createChecking("22222222", 1.0);


        bank.transfer("11111111", "22222222", 100);

        Account cd = bank.getAccount("11111111");
        assertEquals(1000.0, cd.getBalance(), 0.0001);
    }


    @Test
    void getFormattedSummary_includes_savings_correctly() {
        Bank bank = new Bank();
        bank.createSavings("11111111", 1.0);

        String summary = bank.getFormattedSummary();
        assertTrue(summary.contains("Savings"));
        assertTrue(summary.contains("11111111"));
    }

    @Test
    void passTime_with_many_months_executes_loop_body() {
        Bank bank = new Bank();
        bank.createSavings("11111111", 1.0);

        bank.passTime(10);
        Account savings = bank.getAccount("11111111");
        assertEquals(10, savings.getMonthsPassed());
    }

    @Test
    void transfer_from_exactly_12_months_mature_cd_succeeds() {
        Bank bank = new Bank();
        bank.createCD("11111111", 1.0, 1000);
        bank.createChecking("22222222", 1.0);

        bank.passTime(12);

        double cdBalanceBefore = bank.getAccount("11111111").getBalance();
        double checkingBalanceBefore = bank.getAccount("22222222").getBalance();

        bank.transfer("11111111", "22222222", 500);

        assertEquals(cdBalanceBefore - 500,
                bank.getAccount("11111111").getBalance(), 0.1);
        assertEquals(checkingBalanceBefore + 500,
                bank.getAccount("22222222").getBalance(), 0.0001);
    }




    @Test
    void passTime_one_month_executes_loop_exactly_once() {
        Bank bank = new Bank();
        bank.createSavings("11111111", 1.0);

        bank.passTime(1);

        assertEquals(1, bank.getAccount("11111111").getMonthsPassed());
    }

}
