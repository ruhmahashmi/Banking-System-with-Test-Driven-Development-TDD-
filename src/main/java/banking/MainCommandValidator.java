package banking;

public class MainCommandValidator extends CommandValidator {
    private final CreateCommandValidator create = new CreateCommandValidator(bank);
    private final DepositCommandValidator deposit = new DepositCommandValidator(bank);
    private final WithdrawCommandValidator withdraw = new WithdrawCommandValidator(bank);
    private final TransferCommandValidator transfer = new TransferCommandValidator(bank);
    private final PassTimeCommandValidator passTime = new PassTimeCommandValidator();

    public MainCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        if (command == null || command.trim().isEmpty()) return false;
        String type = command.trim().split("\\s+")[0].toLowerCase();
        return switch (type) {
            case "create" -> create.validate(command);
            case "deposit" -> deposit.validate(command);
            case "withdraw" -> withdraw.validate(command);
            case "transfer" -> transfer.validate(command);
            case "pass" -> passTime.validate(command);
            default -> false;
        };
    }
}