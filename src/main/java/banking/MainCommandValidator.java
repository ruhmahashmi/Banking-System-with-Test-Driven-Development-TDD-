package banking;

public class MainCommandValidator extends CommandValidator {
    private final Bank bank;
    private final CreateCommandValidator create;
    private final DepositCommandValidator deposit;
    private final WithdrawCommandValidator withdraw;
    private final TransferCommandValidator transfer;
    private final PassTimeCommandValidator passTime;

    public MainCommandValidator(Bank bank) {
        super(bank);
        this.bank = bank;
        this.create = new CreateCommandValidator(bank);
        this.deposit = new DepositCommandValidator(bank);
        this.withdraw = new WithdrawCommandValidator(bank);
        this.transfer = new TransferCommandValidator(bank);
        this.passTime = new PassTimeCommandValidator();
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
