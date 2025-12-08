package banking;

public class DepositCommandValidator extends CommandValidator {
    public DepositCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }

        String[] parts = command.trim().split("\\s+");
        if (parts.length != 3 || !parts[0].equalsIgnoreCase("deposit")) {
            return false;
        }

        if (!parts[1].matches("\\d{8}")) {
            return false;
        }

        return isValidDepositAmount(parts[2]);
    }

    private boolean isValidDepositAmount(String amountStr) {
        try {
            double amount = Double.parseDouble(amountStr);
            return amount > 0 && amount <= 10000;
        } catch (Exception e) {
            return false;
        }
    }
}
