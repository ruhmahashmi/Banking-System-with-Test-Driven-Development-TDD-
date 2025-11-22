package banking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}