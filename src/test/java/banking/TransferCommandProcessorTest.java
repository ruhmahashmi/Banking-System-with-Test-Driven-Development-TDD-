package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransferCommandProcessorTest {

    private Bank bank;
    private TransferCommandProcessor processor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.createChecking("11111111", 1.0);
        bank.createSavings("22222222", 1.5);
        bank.deposit("11111111", 500.0);
        processor = new TransferCommandProcessor(bank);
    }

    @Test
    void transfer_moves_money_between_accounts() {
        processor.process("transfer 11111111 22222222 200.0");
        assertEquals(300.0, bank.getAccount("11111111").getBalance(), 0.001);
        assertEquals(200.0, bank.getAccount("22222222").getBalance(), 0.001);
    }

    @Test
    void transfer_more_than_balance_does_not_overdraw_source() {
        processor.process("transfer 11111111 22222222 600.0");
        assertTrue(bank.getAccount("11111111").getBalance() >= 0.0);
    }

    @Test
    void transfer_exact_balance_succeeds_and_empties_source() {
        TransferCommandProcessor processor = new TransferCommandProcessor(bank);
        bank.createSavings("sav1", 0.5);
        bank.createChecking("chk1", 0.5);
        bank.deposit("sav1", 1000.0);

        processor.process("transfer sav1 chk1 1000");

        assertEquals(0.0, bank.getAccount("sav1").getBalance(), 0.01);
        assertEquals(1000.0, bank.getAccount("chk1").getBalance(), 0.01);
    }

}
