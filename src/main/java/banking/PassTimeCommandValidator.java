package banking;

public class PassTimeCommandValidator extends CommandValidator {

    public PassTimeCommandValidator() {
        super(null);
    }

    @Override
    public boolean validate(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }

        String[] parts = command.trim().split("\\s+");

        if (parts.length != 2) {
            return false;
        }

        if (!parts[0].equalsIgnoreCase("pass")) {
            return false;
        }

        try {
            int months = Integer.parseInt(parts[1]);
            return months >= 1 && months <= 60;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}