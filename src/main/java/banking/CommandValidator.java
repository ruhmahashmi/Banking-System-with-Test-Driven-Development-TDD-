package banking;

public class CommandValidator {
    protected final Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        return false;
    }
}