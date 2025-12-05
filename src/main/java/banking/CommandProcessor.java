package banking;

import java.util.ArrayList;

public class CommandProcessor {
    private final Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void process(String command) {
        String[] parts = command.trim().split("\\s+");
        String type = parts[0].toLowerCase();

        switch (type) {
            case "create"   -> handleCreate(parts);
            case "deposit"  -> bank.deposit(parts[1], Double.parseDouble(parts[2]));
            case "withdraw" -> bank.withdraw(parts[1], Double.parseDouble(parts[2]));
            case "transfer" -> bank.transfer(parts[1], parts[2], Double.parseDouble(parts[3]));
            case "pass"     -> {
                int months = Integer.parseInt(parts[1]);
                bank.passTime(months);
            }
        }
    }

    private void handleCreate(String[] parts) {
        String type = parts[1].toLowerCase();
        String id = parts[2];
        double apr = Double.parseDouble(parts[3]);

        if (type.equals("cd")) {
            double balance = Double.parseDouble(parts[4]);
            bank.createCD(id, apr, balance);
        } else if (type.equals("checking")) {
            bank.createChecking(id, apr);
        } else if (type.equals("savings")) {
            bank.createSavings(id, apr);
        }
    }
}