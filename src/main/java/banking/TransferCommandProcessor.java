package banking;

public class TransferCommandProcessor {
    private final Bank bank;

    public TransferCommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] parts = command.split(" ");
        String fromId = parts[1];
        String toId = parts[2];
        double amount = Double.parseDouble(parts[3]);

        Account from = bank.getAccount(fromId);
        Account to = bank.getAccount(toId);

        if (from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }
}