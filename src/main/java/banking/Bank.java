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

    public void withdraw(String id, double amount) {
        Account account = getAccount(id);
        if (account != null) {
            account.withdraw(amount);
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
            for (int i = 0; i < months; i++) {
                if (account instanceof Savings savings) {
                    savings.applyMonthlyInterest();
                    if (savings.getBalance() < 1000) {
                        savings.withdraw(25);  // fee
                    }
                } else if (account instanceof CD cd) {
                    cd.applyMonthlyInterest();
                } else if (account instanceof Checking) {
                    account.applyMonthlyInterest();
                }
            }
            account.incrementMonths(months);
        }
    }
}
