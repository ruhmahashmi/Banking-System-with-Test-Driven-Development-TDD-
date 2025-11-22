package sebanking;

public class CommandProcessor {
    private final Bank bank;
    private final InvalidCommandStorage storage;

    public CommandProcessor(Bank bank, InvalidCommandStorage storage) {
        this.bank = bank;
        this.storage = storage;
    }

    public void process(String command) {
        if (command == null || command.trim().isEmpty()) return;
        String[] parts = command.trim().split("\\s+");
        String type = parts[0].toLowerCase();
        switch (type) {
            case "create"  -> handleCreate(parts);
            case "deposit" -> handleDeposit(parts);
        }
    }

    private void handleCreate(String[] parts) {
        String accountType = parts[1].toLowerCase();
        String id = parts[2];
        double apr = Double.parseDouble(parts[3]);
        switch (accountType) {
            case "checking": bank.createChecking(id, apr); break;
            case "savings":  bank.createSavings(id, apr);  break;
            case "cd":       bank.createCD(id, apr, 0.0);  break;
        }
    }

    private void handleDeposit(String[] parts) {
        String id = parts[1];
        double amount = Double.parseDouble(parts[2]);
        bank.deposit(id, amount);
    }
}
