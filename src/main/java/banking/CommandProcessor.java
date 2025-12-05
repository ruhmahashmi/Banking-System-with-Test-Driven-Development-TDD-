// src/main/java/banking/CommandProcessor.java
package banking;

public class CommandProcessor {
    private final Bank bank;

    public CommandProcessor(Bank bank, InvalidCommandStorage storage) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] parts = command.trim().split("\\s+");
        String type = parts[0].toLowerCase();

        switch (type) {
            case "create"   -> handleCreate(parts);
            case "deposit"  -> handleDeposit(parts);
            case "withdraw" -> handleWithdraw(parts);
            case "transfer" -> handleTransfer(parts);
            case "pass"     -> handlePassTime(parts);
        }
    }

    private void handleCreate(String[] parts) {
        String accType = parts[1].toLowerCase();
        String id = parts[2];
        double apr = Double.parseDouble(parts[3]);
        if (accType.equals("cd")) {
            double balance = Double.parseDouble(parts[4]);
            bank.createCD(id, apr, balance);
        } else if (accType.equals("checking")) {
            bank.createChecking(id, apr);
        } else if (accType.equals("savings")) {
            bank.createSavings(id, apr);
        }
    }

    private void handleDeposit(String[] parts) {
        bank.deposit(parts[1], Double.parseDouble(parts[2]));
    }

    private void handleWithdraw(String[] parts) {
        bank.withdraw(parts[1], Double.parseDouble(parts[2]));
    }

    private void handleTransfer(String[] parts) {
        String fromId = parts[1];
        String toId = parts[2];
        double amount = Double.parseDouble(parts[3]);
        bank.transfer(fromId, toId, amount);
    }

    private void handlePassTime(String[] parts) {
        int months = Integer.parseInt(parts[1]);
        bank.passTime(months);

        bank.getAccounts().values().removeIf(acc ->
                !(acc instanceof CD) && acc.getBalance() <= 0.00);
    }
}