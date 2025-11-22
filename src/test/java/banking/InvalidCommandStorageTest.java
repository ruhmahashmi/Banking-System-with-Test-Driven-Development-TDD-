package banking;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InvalidCommandStorageTest {

    @Test
    void initially_has_no_invalid_commands() {
        InvalidCommandStorage storage = new InvalidCommandStorage();
        assertTrue(storage.getAllInvalidCommands().isEmpty());
    }

    @Test
    void can_add_and_retrieve_one_invalid_command() {
        InvalidCommandStorage storage = new InvalidCommandStorage();
        storage.addInvalidCommand("create checking 12345678 999");  // invalid APR
        List<String> commands = storage.getAllInvalidCommands();
        assertEquals(1, commands.size());
        assertEquals("create checking 12345678 999", commands.get(0));
    }

    @Test
    void can_store_multiple_invalid_commands() {
        InvalidCommandStorage storage = new InvalidCommandStorage();
        storage.addInvalidCommand("deposit 99999999 100");
        storage.addInvalidCommand("withdraw 12345678 999999");
        assertEquals(2, storage.getAllInvalidCommands().size());
    }
}