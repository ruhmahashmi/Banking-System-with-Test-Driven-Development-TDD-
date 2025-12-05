package banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainCommandValidatorTest {
    private final Bank bank = new Bank();
    private final MainCommandValidator validator = new MainCommandValidator(bank);


    @Test
    void valid_create_checking_returns_true() {
        assertTrue(validator.validate("create checking 12345678 0.6"));
    }

    @Test
    void valid_create_savings_returns_true() {
        assertTrue(validator.validate("create savings 87654321 1.5"));
    }

    @Test
    void valid_create_cd_returns_true() {
        assertTrue(validator.validate("create cd 11111111 2.0"));
    }

    @Test
    void invalid_create_missing_parts_returns_false() {
        assertFalse(validator.validate("create checking 12345678"));
    }


    @Test
    void valid_deposit_returns_true() {
        assertTrue(validator.validate("deposit 11111111 500"));
    }

    @Test
    void deposit_invalid_id_length_returns_false() {
        assertFalse(validator.validate("deposit 1111111 500"));
    }

    @Test
    void deposit_invalid_amount_zero_returns_false() {
        assertFalse(validator.validate("deposit 11111111 0"));
    }

    @Test
    void deposit_invalid_non_numeric_amount_returns_false() {
        assertFalse(validator.validate("deposit 11111111 fivehundred"));
    }

    @Test
    void invalid_unknown_command_returns_false() {
        assertFalse(validator.validate("foobar"));
    }

    @Test
    void null_command_returns_false() {
        assertFalse(validator.validate(null));
    }

    @Test
    void empty_command_returns_false() {
        assertFalse(validator.validate(""));
    }
}
