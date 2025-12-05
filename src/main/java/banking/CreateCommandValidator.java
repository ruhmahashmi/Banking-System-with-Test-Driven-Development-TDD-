package banking;

import java.util.Set;

public class CreateCommandValidator extends CommandValidator {

    private static final Set<String> VALID_TYPES = Set.of("checking", "savings", "cd");

    public CreateCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }

        String[] parts = command.trim().split("\\s+");

        if (parts.length < 4 || parts.length > 5) {
            return false;
        }

        if (!parts[0].equalsIgnoreCase("create")) {
            return false;
        }

        String accountType = parts[1].toLowerCase();
        if (!VALID_TYPES.contains(accountType)) {
            return false;
        }

        // ID must be exactly 8 digits
        String id = parts[2];
        if (id.length() != 8 || !id.matches("\\d{8}")) {
            return false;
        }

        // Check for duplicate ID
        if (bank.accountExists(id)) {
            return false;
        }

        // APR must be 0.0 to 10.0
        double apr;
        try {
            apr = Double.parseDouble(parts[3]);
            if (apr < 0 || apr > 10) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // CD has 5 parts and balance between 1000 and 10000
        if (accountType.equals("cd")) {
            if (parts.length != 5) {
                return false;
            }
            double initialBalance;
            try {
                initialBalance = Double.parseDouble(parts[4]);
                if (initialBalance < 1000 || initialBalance > 10000) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            // checking and savings must have exactly 4 parts
            if (parts.length != 4) {
                return false;
            }
        }

        return true;
    }
}