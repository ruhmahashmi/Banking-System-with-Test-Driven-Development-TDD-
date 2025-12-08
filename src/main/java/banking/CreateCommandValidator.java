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
        if (parts.length != 4 || !parts[0].equalsIgnoreCase("create")) {
            return false;
        }

        if (!VALID_TYPES.contains(parts[1].toLowerCase()) || !parts[2].matches("\\d{8}")) {
            return false;
        }

        return isValidApr(parts[3]);
    }

    private boolean isValidApr(String aprStr) {
        try {
            double apr = Double.parseDouble(aprStr);
            return apr >= 0 && apr <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
