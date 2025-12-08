package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account;

    @BeforeEach
    void set_up() {
        account = new Checking("12345678", 1.2);
    }


    @Test
    void apr_is_supplied() {
        assertEquals(1.2, account.getApr(), 0.0001);
    }

    @Test
    void deposit_increases_balance() {
        account.deposit(100.5);
        assertEquals(100.5, account.getBalance(), 0.0001);
    }

    @Test
    void withdraw_decreases_balance() {
        account.deposit(100.5);
        account.withdraw(50.25);
        assertEquals(50.25, account.getBalance(), 0.0001);
    }

    @Test
    void withdraw_more_than_balance_sets_to_zero() {
        account.deposit(100.5);
        account.withdraw(200.75);
        assertEquals(0.0, account.getBalance(), 0.0001);
    }

    @Test
    void deposit_twice_works() {
        account.deposit(100.5);
        account.deposit(100.5);
        assertEquals(201.0, account.getBalance(), 0.0001);
    }

    @Test
    void withdraw_twice_works() {
        account.deposit(300.75);
        account.withdraw(100.25);
        account.withdraw(100.25);
        assertEquals(100.25, account.getBalance(), 0.0001);
    }


    @Test
    void withdraw_boundary_killer() {
        Account acc = new Checking("bnd", 0.0);
        assertEquals(0.0, acc.getBalance(), 0.00000001);

        acc.withdraw(0.0);

        assertEquals(0.0, acc.getBalance(), 0.00000001);
    }

    @Test
    void canWithdraw_default_account_returns_true() {
        Account testAccount = new Checking("11111111", 1.0);
        assertTrue(testAccount.canWithdraw());
    }


    @Test
    void withdraw_from_checking_at_limit_400_is_allowed() {
        Bank bank = new Bank();
        bank.createChecking("11111111", 1.0);
        bank.deposit("11111111", 500);
        WithdrawCommandValidator validator = new WithdrawCommandValidator(bank);

        assertTrue(validator.validate("withdraw 11111111 400"));
    }

    @Test
    void withdraw_from_savings_at_limit_1000_is_allowed() {
        Bank bank = new Bank();
        bank.createSavings("22222222", 1.0);
        bank.deposit("22222222", 2000);
        WithdrawCommandValidator validator = new WithdrawCommandValidator(bank);

        assertTrue(validator.validate("withdraw 22222222 1000"));
    }


    @Test
    void passTime_zero_months_does_nothing() {
        account.deposit(1000.0);

        account.passTime(0);

        assertEquals(0, account.getMonthsPassed());
        assertEquals(1000.0, account.getBalance(), 0.0001);
    }

    @Test
    void passTime_two_months_kills_loop_and_calls() {
        Account acc = new Checking("loop", 12.0);
        acc.deposit(100.0);

        acc.passTime(2);

        assertEquals(2, acc.getMonthsPassed());
        assertTrue(acc.getBalance() > 100.0);
    }


    @Test
    void passTime_negative_months_ignored() {
        account.passTime(-1);
        assertEquals(0, account.getMonthsPassed());
    }

    @Test
    void passTime_max_months_boundary() {
        account.deposit(1000.0);
        account.passTime(60);
        assertEquals(60, account.getMonthsPassed());
    }

    @Test
    void passTime_loop_runs_precisely() {
        account.deposit(1000.0);
        int months = 2;
        account.passTime(months);
        assertEquals(months, account.getMonthsPassed());
    }

    @Test
    void passTime_loop_body_execution() {
        Account testAccount = new Checking("test", 1.2);
        testAccount.deposit(1000.0);

        testAccount.passTime(1);

        assertEquals(1, testAccount.getMonthsPassed());
        assertEquals(1001.0, testAccount.getBalance(), 0.1);
    }

    @Test
    void passTime_loop_body_executes_in_account() {
        Account account = new Checking("12345678", 1.0);
        account.deposit(1000);

        account.passTime(2);

        assertEquals(2, account.getMonthsPassed());
        assertTrue(account.getBalance() > 1000);
    }



}
