package sebanking;

public class MainCommandValidator extends CommandValidator {
    private final CreateCommandValidator createValidator = new CreateCommandValidator();
    private final DepositCommandValidator depositValidator = new DepositCommandValidator();

    @Override
    public boolean validate(String command) {
        if (command == null || command.trim().isEmpty()) return false;
        String[] parts = command.trim().split("\\s+");
        if (parts.length == 0) return false;
        String type = parts[0].toLowerCase();
        if ("create".equals(type)) return createValidator.validate(command);
        if ("deposit".equals(type)) return depositValidator.validate(command);
        return false;
    }
}
