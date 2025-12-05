package banking;

public class TransferCommandValidator {
    private final Bank bank;

    public TransferCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 4 || !parts[0].equalsIgnoreCase("transfer")) return false;
        if (!parts[1].matches("\\d{8}") || !parts[2].matches("\\d{8}")) return false;
        if (!bank.accountExists(parts[1]) || !bank.accountExists(parts[2])) return false;

        Account from = bank.getAccount(parts[1]);
        Account to = bank.getAccount(parts[2]);

        boolean fromIsCheckingOrSavings = from instanceof Checking || from instanceof Savings;
        boolean toIsCheckingOrSavings = to instanceof Checking || to instanceof Savings;

        return fromIsCheckingOrSavings && toIsCheckingOrSavings && from != to;
    }
}