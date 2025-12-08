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
        if (!canWithdraw(account, amount)) {
            return;
        }

        boolean wasFullCdWithdrawal = isFullCdWithdrawal(account, amount);
        account.withdraw(amount);

        if (wasFullCdWithdrawal) {
            accounts.remove(id);
        }
    }

    private boolean canWithdraw(Account account, double amount) {
        if (account == null) return false;
        if (account instanceof CD && account.getMonthsPassed() < 12) {
            throw new IllegalStateException("CD is not mature yet");
        }
        return amount <= account.getBalance();
    }

    private boolean isFullCdWithdrawal(Account account, double amount) {
        if (!(account instanceof CD)) return false;
        CD cd = (CD) account;
        return amount >= account.getBalance() || amount == cd.getInitialBalance();
    }

    public void transfer(String fromId, String toId, double amount) {
        Account from = getAccount(fromId);
        Account to = getAccount(toId);

        if (!canTransfer(from, to, amount)) {
            return;
        }

        from.withdraw(amount);
        to.deposit(amount);
    }

    private boolean canTransfer(Account from, Account to, double amount) {
        return from != null && to != null &&
                !(from instanceof CD && from.getMonthsPassed() < 12) &&
                from.getBalance() >= amount;
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
            String type = getAccountType(account);
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

    private String getAccountType(Account account) {
        if (account instanceof Checking) return "Checking";
        if (account instanceof Savings) return "Savings";
        return "CD";
    }

    public java.util.Collection<Account> getAccounts() {
        return accounts.values();
    }
}
