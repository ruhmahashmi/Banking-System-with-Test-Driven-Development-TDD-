package banking;

public class CommandValidator {
    private final Bank bank;  // ADD THIS LINE

    public CommandValidator(Bank bank) {  // ADD THIS CONSTRUCTOR
        this.bank = bank;
    }

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
        } else if ("transfer".equals(type)) {
            TransferCommandValidator transferValidator = new TransferCommandValidator(bank);
            return transferValidator.validate(command);
        }

        return false;
    }
}