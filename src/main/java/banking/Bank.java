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


}
