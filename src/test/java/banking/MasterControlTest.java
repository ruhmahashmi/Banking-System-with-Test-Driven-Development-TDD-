package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class MasterControlTest {

    private MasterControl masterControl;
    private Bank bank;
    private InvalidCommandStorage storage;
    private List<String> input;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        masterControl = new MasterControl(bank);
        storage = masterControl.getInvalidCommandStorage();
        input = new ArrayList<>();
    }

    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
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


    @Test
    void pass_command_through_start_updates_balances() {
        Bank bank = new Bank();
        MasterControl masterControl = new MasterControl(bank);

        bank.createSavings("12345678", 12.0);
        bank.deposit("12345678", 1000.0);

        List<String> input = new ArrayList<>();
        input.add("Pass 1");

        List<String> output = masterControl.start(input);

        String summary = output.get(output.size() - 1);
        assertTrue(summary.contains("12345678"));
    }

    @Test
    void masterControlStartAlwaysProducesOutput() {
        MasterControl mc = new MasterControl(new Bank());

        List<String> emptyResult = mc.start(Collections.emptyList());
        System.out.println("Empty input size: " + emptyResult.size());

        List<String> whitespaceResult = mc.start(List.of("   "));
        System.out.println("Whitespace size: " + whitespaceResult.size());
        assertEquals(emptyResult.size(), whitespaceResult.size());
    }

    @Test
    void executeCommand_prints_first_line_when_output_not_empty() {
        Bank bank = new Bank();
        MasterControl mc = new MasterControl(bank);

        List<String> input = List.of(
                "create checking 12345678 0.6",
                "deposit 12345678 100",
                "pass 1"
        );
        List<String> output = mc.start(input);
        assertFalse(output.isEmpty(), "Precondition: start must produce non-empty output");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));

        try {
            mc.executeCommand("sample make_sure_this_passes_unchanged_or_you_will_fail");
        } finally {
            System.setOut(originalOut);
        }

        String printed = baos.toString().trim();
        assertFalse(printed.isEmpty(), "executeCommand must print when output is not empty");
    }

    @Test
    void executeCommand_prints_message_when_output_empty() {
        Bank bank = new Bank();
        MasterControl mc = new MasterControl(bank);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));

        try {
            mc.executeCommand("   ");
        } finally {
            System.setOut(originalOut);
        }

        String printed = baos.toString().trim();
        System.out.println("DEBUG printed for empty: [" + printed + "]");
        assertFalse(printed.isEmpty());
    }




    @Test
    void executeCommand_prints_output_when_not_empty() {
        Bank bank = new Bank();
        MasterControl mc = new MasterControl(bank);

        String command = "create checking 12345678 0.6";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));

        try {
            mc.executeCommand(command);
        } finally {
            System.setOut(originalOut);
        }

        String printed = baos.toString().trim();
        assertFalse(printed.isEmpty());
    }



}
