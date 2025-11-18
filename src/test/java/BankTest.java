package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
