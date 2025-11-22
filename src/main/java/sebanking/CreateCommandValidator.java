package sebanking;

public class CreateCommandValidator extends CommandValidator {

    @Override
    public boolean validate(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }

        String[] parts = command.trim().split("\\s+");

        if (parts.length != 4) {
            return false;
        }

        String type = parts[0].toLowerCase();
        if (!"create".equals(type)) {
            return false;
        }

        String accountType = parts[1].toLowerCase();
        if (!"checking".equals(accountType) && !"savings".equals(accountType) && !"cd".equals(accountType)) {
            return false;
        }

        String id = parts[2];
        if (id.length() != 8 || !id.matches("\\d+")) {
            return false;
        }

        try {
            double apr = Double.parseDouble(parts[3]);
            if (apr < 0 || apr > 10) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
