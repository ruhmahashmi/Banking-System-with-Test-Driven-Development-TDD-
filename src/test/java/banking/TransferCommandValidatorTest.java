package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransferCommandValidatorTest {

    private Bank bank;
    private TransferCommandValidator validator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.createChecking("11111111", 1.0);
        bank.createSavings("22222222", 1.5);
        validator = new TransferCommandValidator(bank);
    }

    @Test
    void valid_transfer_between_checking_and_savings_returns_true() {
        assertTrue(validator.validate("transfer 11111111 22222222 100.0"));
    }

    @Test
    void invalid_keyword_returns_false() {
        assertFalse(validator.validate("move 11111111 22222222 100.0"));
    }

    @Test
    void invalid_id_format_returns_false() {
        assertFalse(validator.validate("transfer 1111111a 22222222 100.0"));
    }

    @Test
    void non_existent_source_or_target_returns_false() {
        assertFalse(validator.validate("transfer 33333333 22222222 100.0"));
        assertFalse(validator.validate("transfer 11111111 33333333 100.0"));
    }

    @Test
    void transfer_from_account_to_itself_returns_false() {
        assertFalse(validator.validate("transfer 11111111 11111111 100.0"));
    }

    @Test
    void transfer_from_checking_to_cd_returns_false() {
        TransferCommandValidator validator = new TransferCommandValidator(bank);
        bank.createChecking("chk1", 0.5);
        bank.createCD("cd1", 0.5, 1000.0);

        boolean valid = validator.validate("transfer chk1 cd1 100");

        assertFalse(valid);
    }

    @Test
    void transfer_from_cd_to_checking_returns_false() {
        TransferCommandValidator validator = new TransferCommandValidator(bank);
        bank.createCD("cd1", 0.5, 1000.0);
        bank.createChecking("chk1", 0.5);

        boolean valid = validator.validate("transfer cd1 chk1 100");

        assertFalse(valid);
    }


    @Test
    void transfer_from_savings_to_savings_returns_false() {
        TransferCommandValidator validator = new TransferCommandValidator(bank);
        bank.createSavings("sav1", 0.5);
        bank.createSavings("sav2", 0.5);

        boolean valid = validator.validate("transfer sav1 sav2 100");

        assertFalse(valid);
    }


}
