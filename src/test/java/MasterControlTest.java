package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MasterControlTest {
    private MasterControl masterControl;
    private Bank bank;
    private InvalidCommandStorage storage;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        CommandValidator validator = new MainCommandValidator();
        CommandProcessor processor = new CommandProcessor(bank, storage);
        storage = new InvalidCommandStorage();
        masterControl = new MasterControl(validator, processor, storage);
    }

    @Test
    void invalid_command_is_stored_and_not_processed() {
        masterControl.executeCommand("create checking 123456789 1.0");
        assertTrue(storage.getAllInvalidCommands().contains("create checking 123456789 1.0"));
        assertEquals(0, bank.getNumberOfAccounts());
    }

    @Test
    void valid_create_command_is_processed_and_not_stored() {
        masterControl.executeCommand("create checking 12345678 2.5");
        assertEquals(1, bank.getNumberOfAccounts());
        assertTrue(storage.getAllInvalidCommands().isEmpty());
    }

    @Test
    void valid_deposit_is_processed() {
        masterControl.executeCommand("create savings 11111111 0.1");
        masterControl.executeCommand("deposit 11111111 500");
        assertEquals(500.0, bank.getAccount("11111111").getBalance(), 0.001);
        assertTrue(storage.getAllInvalidCommands().isEmpty());
    }

    @Test
    void multiple_invalid_commands_are_all_stored() {
        masterControl.executeCommand("foobar");
        masterControl.executeCommand("create checking 12 1.0");
        masterControl.executeCommand("deposit 123");
        assertEquals(3, storage.getAllInvalidCommands().size());
    }
}
