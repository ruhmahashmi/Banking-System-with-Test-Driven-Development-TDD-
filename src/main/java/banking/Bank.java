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
        Checking checking = new Checking(id, apr);
        addAccount(checking);
    }

    public void createSavings(String id, double apr) {
        Savings savings = new Savings(id, apr);
        addAccount(savings);
    }

    public void createCD(String id, double apr, double initialBalance) {
        CD cd = new CD(id, apr, initialBalance);
        addAccount(cd);
    }

    public void passTime(int months) {
        if (months <= 0) return;

        for (Account account : accounts.values()) {
            account.incrementMonths(months);
            if (account instanceof CD) {
                // CD compounds monthly but doesn't pay until withdrawal
                for (int i = 0; i < months; i++) {
                    account.applyMonthlyInterest();
                }
            } else if (account instanceof Savings savings) {
                for (int i = 0; i < months; i++) {
                    savings.applyMonthlyInterest();
                    if (savings.getBalance() < 1000) {
                        savings.withdraw(25);
                        savings.balance = Math.max(0, savings.getBalance()); // cannot go negative
                    }
                }
            } else if (account instanceof Checking) {
                // Checking earns no interest
            }
        }
    }

    public void withdraw(String id, double amount) {
        Account account = getAccount(id);
        if (account == null) {
            return;
        }

        // CD maturity check
        if (account instanceof CD && account.getMonthsPassed() < 12) {
            throw new IllegalStateException("CD is not mature yet");
        }

        account.withdraw(amount);

        // Remove CD if balance is zero (after full withdrawal)
        if (account instanceof CD && account.getBalance() <= 0.00) {
            accounts.remove(id);
        }
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
                    .append(String.format("%.2f", account.getApr()));

            if (account instanceof Savings s && s.getBalance() < 1000) {
                sb.append(" WARNING: Balance below $1000");
            }
            if (account instanceof CD cd && cd.getMonthsPassed() < 12) {
                sb.append(" WARNING: CD not mature");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public void transfer(String fromId, String toId, double amount) {
        Account from = getAccount(fromId);
        Account to = getAccount(toId);
        if (from == null || to == null) return;

        if (from instanceof CD && from.getMonthsPassed() < 12) return;

        if (from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }

    public java.util.Collection<Account> getAccounts() {
        return accounts.values();
    }


}
