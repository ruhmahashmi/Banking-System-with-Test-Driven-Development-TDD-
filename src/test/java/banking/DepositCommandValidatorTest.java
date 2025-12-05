package banking;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepositCommandValidatorTest {
    private final Bank bank = new Bank();
    private final DepositCommandValidator validator = new DepositCommandValidator(bank);

    @Test
    void valid_deposit_returns_true() {
        assertTrue(validator.validate("deposit 12345678 100.5"));
    }

    @Test
    void invalid_id_length_returns_false() {
        assertFalse(validator.validate("deposit 1234567 100.5"));
    }

    @Test
    void invalid_id_format_returns_false() {
        assertFalse(validator.validate("deposit 12345abc 100.5"));
    }

    @Test
    void invalid_amount_negative_returns_false() {
        assertFalse(validator.validate("deposit 12345678 -100.5"));
    }

    @Test
    void invalid_amount_zero_returns_false() {
        assertFalse(validator.validate("deposit 12345678 0"));
    }

    @Test
    void invalid_amount_non_numeric_returns_false() {
        assertFalse(validator.validate("deposit 12345678 abc"));
    }

    @Test
    void missing_parts_returns_false() {
        assertFalse(validator.validate("deposit 12345678"));
    }

    @Test
    void extra_parts_returns_false() {
        assertFalse(validator.validate("deposit 12345678 100.5 extra"));
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
        assertFalse(validator.validate("deposits 12345678 100.5"));
    }

    @Test
    void leading_spaces_returns_true() {
        assertTrue(validator.validate(" deposit 12345678 100.5"));
    }

    @Test
    void trailing_spaces_returns_true() {
        assertTrue(validator.validate("deposit 12345678 100.5 "));
    }

    @Test
    void multiple_spaces_returns_true() {
        assertTrue(validator.validate("deposit  12345678  100.5"));
    }
}