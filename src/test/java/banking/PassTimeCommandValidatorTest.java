package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassTimeCommandValidatorTest {

    private final PassTimeCommandValidator validator = new PassTimeCommandValidator();

    @Test
    void valid_pass_one_month_returns_true() {
        assertTrue(validator.validate("pass 1"));
    }

    @Test
    void valid_pass_sixty_months_returns_true() {
        assertTrue(validator.validate("pass 60"));
    }

    @Test
    void zero_months_returns_false() {
        assertFalse(validator.validate("pass 0"));
    }

    @Test
    void too_many_months_returns_false() {
        assertFalse(validator.validate("pass 61"));
    }

    @Test
    void non_numeric_months_returns_false() {
        assertFalse(validator.validate("pass abc"));
    }

    @Test
    void wrong_keyword_returns_false() {
        assertFalse(validator.validate("skip 12"));
    }

    @Test
    void null_or_empty_return_false() {
        assertFalse(validator.validate(null));
        assertFalse(validator.validate(""));
    }

    @Test
    void extra_parts_in_pass_time_command_return_false() {
        PassTimeCommandValidator validator = new PassTimeCommandValidator();
        assertFalse(validator.validate("pass 12 extra"));
    }

}
