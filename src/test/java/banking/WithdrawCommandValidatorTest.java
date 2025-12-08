package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawCommandValidatorTest {

    private Bank bank;
    private WithdrawCommandValidator validator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.createChecking("11111111", 1.0);
        bank.createSavings("22222222", 1.5);
        bank.createCD("33333333", 2.0, 2000.0);
        validator = new WithdrawCommandValidator(bank);
    }

    @Test
    void valid_withdraw_from_checking_under_limit_returns_true() {
        bank.deposit("11111111", 500.0);
        assertTrue(validator.validate("withdraw 11111111 100.0"));
    }

    @Test
    void withdraw_over_checking_limit_returns_false() {
        bank.deposit("11111111", 1000.0);
        assertFalse(validator.validate("withdraw 11111111 401.0"));
    }

    @Test
    void withdraw_over_savings_limit_returns_false() {
        bank.deposit("22222222", 2000.0);
        assertFalse(validator.validate("withdraw 22222222 1000.1"));
    }

    @Test
    void withdraw_from_cd_before_12_months_returns_false() {
        assertFalse(validator.validate("withdraw 33333333 100.0"));
    }

    @Test
    void non_numeric_amount_returns_false() {
        assertFalse(validator.validate("withdraw 11111111 abc"));
    }

    @Test
    void invalid_id_or_keyword_returns_false() {
        assertFalse(validator.validate("withdraw 1111111 100.0"));
        assertFalse(validator.validate("take 11111111 100.0"));
    }

    @Test
    void withdraw_from_cd_at_12_months_is_allowed_by_validator() {
        Bank bank = new Bank();
        bank.createCD("11111111", 2.0, 2000.0);
        bank.getAccount("11111111").passTime(12);

        WithdrawCommandValidator validator = new WithdrawCommandValidator(bank);
        assertTrue(validator.validate("withdraw 11111111 100"));
    }

    @Test
    void withdraw_zero_amount_returns_false() {
        Bank bank = new Bank();
        bank.createChecking("11111111", 1.0);
        bank.deposit("11111111", 500);

        WithdrawCommandValidator validator = new WithdrawCommandValidator(bank);
        assertFalse(validator.validate("withdraw 11111111 0"));
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
    void withdraw_from_nonexistent_account_returns_false() {
        Bank bank = new Bank();
        WithdrawCommandValidator validator = new WithdrawCommandValidator(bank);

        assertFalse(validator.validate("withdraw 99999999 100"));
    }


}
