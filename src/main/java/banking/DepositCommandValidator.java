package banking;

public class DepositCommandValidator extends CommandValidator {

    public DepositCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        if (command == null || command.trim().isEmpty()) return false;

        String[] parts = command.trim().split("\\s+");

        if (parts.length != 3 || !parts[0].equalsIgnoreCase("deposit")) return false;
        if (!parts[1].matches("\\d{8}")) return false;
        try {
            double amount = Double.parseDouble(parts[2]);
            return amount > 0 && amount <= 10000;
        } catch (Exception e) {
            return false;
        }
    }
}
