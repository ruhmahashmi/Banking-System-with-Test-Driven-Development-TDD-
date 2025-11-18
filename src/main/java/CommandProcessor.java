package banking;

public class CommandProcessor {
    private final Bank bank;
    private final InvalidCommandStorage storage;

    public CommandProcessor(Bank bank, InvalidCommandStorage storage) {
        this.bank = bank;
        this.storage = storage;
    }

    public void process(String command) {
        if (command == null) {
            storage.addInvalidCommand("null");
            return;
        }

        command = command.trim();
        if (command.isEmpty()) {
            storage.addInvalidCommand(command);
            return;
        }

        if (!isValid(command)) {
            storage.addInvalidCommand(command);
            return;
        }

        String[] parts = command.toLowerCase().split("\\s+");
        String type = parts[0];

        switch (type) {
            case "create"  -> handleCreate(parts);
            case "deposit" -> handleDeposit(parts);
        }
    }

    private boolean isValid(String command) {
        String[] parts = command.toLowerCase().split("\\s+");
        if (parts.length == 0) return false;

        return switch (parts[0]) {
            case "create"  -> new CreateCommandValidator().validate(command);
            case "deposit" -> new DepositCommandValidator().validate(command);
            default        -> false;
        };
    }

    private void handleCreate(String[] parts) {
        String type = parts[1];
        String id = parts[2];
        double apr = Double.parseDouble(parts[3]);

        switch (type) {
            case "checking" -> bank.createChecking(id, apr);
            case "savings"  -> bank.createSavings(id, apr);
            case "cd"       -> {
                double balance = Double.parseDouble(parts[4]);
                bank.createCD(id, apr, balance);
            }
        }
    }

    private void handleDeposit(String[] parts) {
        String id = parts[1];
        double amount = Double.parseDouble(parts[2]);
        bank.deposit(id, amount);
    }
}