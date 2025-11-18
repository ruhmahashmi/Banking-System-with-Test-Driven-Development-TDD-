package banking;
public class CommandValidator {

    public boolean validate(String command) {
        if (command == null || command.trim().isEmpty()) {
            return false;
        }

        String[] parts = command.trim().split("\\s+");
        if (parts.length < 1) {
            return false;
        }

        String type = parts[0].toLowerCase();
        if ("create".equals(type)) {
            CreateCommandValidator createValidator = new CreateCommandValidator();
            return createValidator.validate(command);
        } else if ("deposit".equals(type)) {
            DepositCommandValidator depositValidator = new DepositCommandValidator();
            return depositValidator.validate(command);
        }

        return false;
    }
}