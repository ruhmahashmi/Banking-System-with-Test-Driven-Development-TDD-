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
        processor = new CommandProcessor(bank);
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
}