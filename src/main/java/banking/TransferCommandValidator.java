package banking;

public class TransferCommandValidator {
    private final Bank bank;

    public TransferCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String[] parts = command.split(" ");
        if (parts.length != 4 || !parts[0].equalsIgnoreCase("transfer")) {
            return false;
        }

        if (!isValidAccountIds(parts[1], parts[2])) {
            return false;
        }

        Account from = bank.getAccount(parts[1]);
        Account to = bank.getAccount(parts[2]);

        return isValidTransferAccounts(from, to);
    }

    private boolean isValidAccountIds(String fromId, String toId) {
        return fromId.matches("\\d{8}") &&
                toId.matches("\\d{8}") &&
                bank.accountExists(fromId) &&
                bank.accountExists(toId);
    }

    private boolean isValidTransferAccounts(Account from, Account to) {
        boolean fromIsCheckingOrSavings = from instanceof Checking || from instanceof Savings;
        boolean toIsCheckingOrSavings = to instanceof Checking || to instanceof Savings;
        return fromIsCheckingOrSavings && toIsCheckingOrSavings && from != to;
    }
}
