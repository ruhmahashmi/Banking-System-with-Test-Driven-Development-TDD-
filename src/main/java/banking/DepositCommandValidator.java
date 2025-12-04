package banking;
public class DepositCommandValidator extends CommandValidator {

    @Override
    public boolean validate(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }

        String[] parts = command.trim().split("\\s+");


        if (parts.length != 3) {
            return false;
        }

        String type = parts[0].toLowerCase();
        if (!"deposit".equals(type)) {
            return false;
        }

        String id = parts[1];
        if (id.length() != 8 || !id.matches("\\d+")) {
            return false;
        }

        try {
            double amount = Double.parseDouble(parts[2]);
            if (amount <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}