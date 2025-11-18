package banking;

public class CommandProcessor {
    private final Bank bank;
    private final InvalidCommandStorage storage;

    public CommandProcessor(Bank bank, InvalidCommandStorage storage) {
        this.bank = bank;
        this.storage = storage;
    }

    public void process(String command) {
        if (!isValid(command)) {
            storage.addInvalidCommand(command);
            return;
        }
    }

    private boolean isValid(String command) {
        String[] parts = command.toLowerCase().split(" ");
        String type = parts[0];

        return switch (type) {
            case "create" -> new CreateCommandValidator().validate(command);  // Instance!
            case "deposit" -> new DepositCommandValidator().validate(command);  // Instance!
            default -> false;
        };
    }
}