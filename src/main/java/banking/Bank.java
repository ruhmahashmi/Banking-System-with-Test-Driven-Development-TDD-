package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public boolean accountExists(String id) {
        return accounts.containsKey(id);
    }

    public void deposit(String id, double amount) {
        Account account = getAccount(id);
        if (account != null) {
            account.deposit(amount);
        }
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public void createChecking(String id, double apr) {
        addAccount(new Checking(id, apr));
    }

    public void createSavings(String id, double apr) {
        addAccount(new Savings(id, apr));
    }

    public void createCD(String id, double apr, double initialBalance) {
        addAccount(new CD(id, apr, initialBalance));
    }


    public void withdraw(String id, double amount) {
        Account account = getAccount(id);
        if (account == null) {
            return;
        }

        if (account instanceof CD && account.getMonthsPassed() < 12) {
            throw new IllegalStateException("CD is not mature yet");
        }

        double currentBalance = account.getBalance();
        if (amount > currentBalance) {
            return;
        }

        boolean isCd = account instanceof CD;
        boolean fullWithdrawal = false;

        if (isCd) {
            CD cd = (CD) account;
            if (amount >= currentBalance || amount == cd.getInitialBalance()) {
                fullWithdrawal = true;
            }
        }

        account.withdraw(amount);

        if (isCd && fullWithdrawal) {
            accounts.remove(id);
        }
    }






    public void transfer(String fromId, String toId, double amount) {
        Account from = getAccount(fromId);
        Account to = getAccount(toId);

        if (from == null || to == null) return;
        if (from instanceof CD && from.getMonthsPassed() < 12) return;
        if (from.getBalance() < amount) return;

        from.withdraw(amount);
        to.deposit(amount);
    }

    public boolean passTime(int months) {
        for (int m = 0; m < months; m++) {
            for (Account account : accounts.values()) {
                account.passTime(1);
            }
        }
        return true;
    }

    public String getFormattedSummary() {
        StringBuilder sb = new StringBuilder();
        for (Account account : accounts.values()) {
            String type = account instanceof Checking ? "Checking" :
                    account instanceof Savings ? "Savings" : "CD";

            sb.append(type)
                    .append(" ")
                    .append(account.getId())
                    .append(" ")
                    .append(String.format("%.2f", account.getBalance()))
                    .append(" ")
                    .append(String.format("%.2f", account.getApr()))
                    .append("\n");
        }
        return sb.toString().trim();
    }

    public java.util.Collection<Account> getAccounts() {
        return accounts.values();
    }
}
