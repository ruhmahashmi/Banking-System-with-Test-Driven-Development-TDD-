package banking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateCommandValidatorTest {
    private final Bank bank = new Bank();
    private final CreateCommandValidator validator = new CreateCommandValidator(bank);

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
    void invalid_type_returns_false() {
        assertFalse(validator.validate("create current 12345678 0.6"));
    }

    @Test
    void invalid_id_length_returns_false() {
        assertFalse(validator.validate("create checking 1234567 0.6"));
    }

    @Test
    void invalid_id_format_returns_false() {
        assertFalse(validator.validate("create checking 12345abc 0.6"));
    }

    @Test
    void invalid_apr_negative_returns_false() {
        assertFalse(validator.validate("create checking 12345678 -1.5"));
    }

    @Test
    void invalid_apr_high_returns_false() {
        assertFalse(validator.validate("create checking 12345678 11.0"));
    }

    @Test
    void invalid_apr_non_numeric_returns_false() {
        assertFalse(validator.validate("create checking 12345678 abc"));
    }

    @Test
    void missing_parts_returns_false() {
        assertFalse(validator.validate("create checking 12345678"));
    }

    @Test
    void extra_parts_returns_false() {
        assertFalse(validator.validate("create checking 12345678 0.6 extra"));
    }

    @Test
    void empty_command_returns_false() {
        assertFalse(validator.validate(""));
    }

    @Test
    void null_command_returns_false() {
        assertFalse(validator.validate(null));
    }

    @Test
    void wrong_keyword_returns_false() {
        assertFalse(validator.validate("makes checking 12345678 0.6"));
    }

    @Test
    void leading_spaces_returns_true() {
        assertTrue(validator.validate(" create checking 12345678 0.6"));
    }

    @Test
    void trailing_spaces_returns_true() {
        assertTrue(validator.validate("create checking 12345678 0.6 "));
    }

    @Test
    void multiple_spaces_returns_true() {
        assertTrue(validator.validate("create  checking  12345678  0.6"));
    }
}
