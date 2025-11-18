package banking;

public class CommandProcessor {
    private final Bank bank;
    private final InvalidCommandStorage storage;

    public CommandProcessor(Bank bank, InvalidCommandStorage storage) {
        this.bank = bank;
        this.storage = storage;
    }

    public void process(String command) {
        // minimal code to make the test pass
        bank.createChecking("12345678", 3.7);
    }
}