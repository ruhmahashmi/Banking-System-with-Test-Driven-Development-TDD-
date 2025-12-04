package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandProcessorTest {
    private Bank bank;
    private InvalidCommandStorage storage;
    private CommandProcessor processor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        storage = new InvalidCommandStorage();
        processor = new CommandProcessor(bank, storage);
    }

    @Test
    void createCheckingCommand_createsCheckingAccountWithCorrectIdAndApr() {
        processor.process("create checking 12345678 0.5");
        Account account = bank.getAccount("12345678");
        assertNotNull(account);
        assertEquals("12345678", account.getId());
        assertEquals(0.5, account.getApr(), 0.001);
    }

    @Test
    void valid_command_is_processed_and_not_stored() {
        processor.process("create savings 87654321 1.2");
        Account account = bank.getAccount("87654321");
        assertNotNull(account);
        assertTrue(storage.getAllInvalidCommands().isEmpty());
    }

    @Test
    void processor_processes_any_command_given() {
        processor.process("create checking 123 1.0");
        Account account = bank.getAccount("123");
        assertNotNull(account);
        assertEquals("123", account.getId());
        assertEquals(1.0, account.getApr(), 0.001);
        assertTrue(storage.getAllInvalidCommands().isEmpty());
    }

    @Test
    void deposit_command_increases_balance() {
        processor.process("create savings 22222222 0.5");
        processor.process("deposit 22222222 150.0");
        Account account = bank.getAccount("22222222");
        assertEquals(150.0, account.getBalance(), 0.001);
    }
}
