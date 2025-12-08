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
        processor = new CommandProcessor(bank);
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

    @Test
    void withdraw_command_decreases_balance_via_processor() {
        Bank bank = new Bank();
        CommandProcessor processor = new CommandProcessor(bank);

        bank.createChecking("11111111", 1.0);
        bank.deposit("11111111", 500.0);

        processor.process("withdraw 11111111 200");

        assertEquals(300.0, bank.getAccount("11111111").getBalance(), 0.001);
    }

    @Test
    void pass_command_advances_time_via_processor() {
        Bank bank = new Bank();
        CommandProcessor processor = new CommandProcessor(bank);

        bank.createSavings("22222222", 12.0);
        bank.deposit("22222222", 1000.0);

        processor.process("pass 1");
        assertTrue(bank.getAccount("22222222").getBalance() > 1000.0);
    }

    @Test
    void transfer_command_uses_bank_transfer() {
        Bank bank = new Bank();
        CommandProcessor processor = new CommandProcessor(bank);

        bank.createChecking("11111111", 1.0);
        bank.createSavings("22222222", 1.0);
        bank.deposit("11111111", 400.0);

        processor.process("transfer 11111111 22222222 150");

        assertEquals(250.0, bank.getAccount("11111111").getBalance(), 0.001);
        assertEquals(150.0, bank.getAccount("22222222").getBalance(), 0.001);
    }


    @Test
    void processorIgnoresInvalidFormatGracefully() {
        CommandProcessor processor = new CommandProcessor(bank);
        processor.process("CREATE_CHECKING 12345678 1.0 extra");
    }

    @Test
    void createCdCommand_createsCdAccountWithCorrectIdAprAndBalance() {
        Bank bank = new Bank();
        CommandProcessor processor = new CommandProcessor(bank);

        processor.process("create cd 99999999 1.5 5000");

        Account account = bank.getAccount("99999999");
        assertNotNull(account);
        assertTrue(account instanceof CD);
        assertEquals(1.5, account.getApr(), 0.0001);
        assertEquals(5000.0, account.getBalance(), 0.0001);
    }


}
