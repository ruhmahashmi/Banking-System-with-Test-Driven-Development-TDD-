package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandProcessorTest {

    private Bank bank;
    private CommandProcessor processor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        InvalidCommandStorage storage = new InvalidCommandStorage();
        processor = new CommandProcessor(bank, storage);  // TWO parameters!
    }

    @Test
    void createCheckingCommand_createsCheckingAccountWithCorrectIdAndApr() {
        processor.process("create checking 12345678 3.7");

        assertTrue(bank.accountExists("12345678"));
        Account account = bank.getAccount("12345678");
        assertTrue(account instanceof Checking);
        assertEquals(3.7, account.getApr());
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void invalid_command_is_stored_in_storage() {
        InvalidCommandStorage storage = new InvalidCommandStorage();
        CommandProcessor processor = new CommandProcessor(bank, storage);

        processor.process("create checking 12345678 999"); // invalid APR

        assertEquals(1, storage.getAllInvalidCommands().size());
        assertEquals("create checking 12345678 999", storage.getAllInvalidCommands().get(0));
    }

    @Test
    void invalid_create_command_is_stored_and_not_processed() {
        InvalidCommandStorage storage = new InvalidCommandStorage();
        CommandProcessor processor = new CommandProcessor(bank, storage);

        processor.process("create checking 12345678 999"); // invalid APR

        assertEquals(1, storage.getAllInvalidCommands().size());
        assertFalse(bank.accountExists("12345678")); // not created!
    }

    @Test
    void valid_command_is_processed_and_not_stored() {
        InvalidCommandStorage storage = new InvalidCommandStorage();
        CommandProcessor processor = new CommandProcessor(bank, storage);

        processor.process("create checking 12345678 2.5");

        assertTrue(storage.getAllInvalidCommands().isEmpty());
        assertTrue(bank.accountExists("12345678"));
    }
}