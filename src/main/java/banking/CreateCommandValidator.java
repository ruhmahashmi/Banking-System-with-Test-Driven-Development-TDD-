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

        if (parts.length != 4) {
            return false;
        }

        if (!parts[0].equalsIgnoreCase("create")) {
            return false;
        }

        String type = parts[1].toLowerCase();
        if (!VALID_TYPES.contains(type)) {
            return false;
        }

        String id = parts[2];
        if (!id.matches("\\d{8}")) {
            return false;
        }

        try {
            double apr = Double.parseDouble(parts[3]);
            if (apr < 0 || apr > 10) return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
