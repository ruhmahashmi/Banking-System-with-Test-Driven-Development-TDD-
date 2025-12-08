
package banking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandValidatorTest {

    @Test
    void base_validate_always_returns_false() {
        Bank bank = new Bank();
        CommandValidator validator = new CommandValidator(bank);

        assertFalse(validator.validate("anything at all"));
    }

}
