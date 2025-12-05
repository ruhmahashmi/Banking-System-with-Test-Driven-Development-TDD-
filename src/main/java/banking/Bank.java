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

    public void withdraw(String id, double amount) {
        Account account = getAccount(id);
        if (account == null) {
            return;
        }

        if (account instanceof CD && account.getMonthsPassed() < 12) {
            throw new IllegalStateException("CD is not mature yet");
        }

        account.withdraw(amount);

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
        if (from == null || to == null || from.getBalance() < amount) return;

        if (from instanceof CD && from.getMonthsPassed() < 12) return;

        from.withdraw(amount);
        to.deposit(amount);
    }

    public void passTime(int months) {
        if (months < 1 || months > 60) return;

        java.util.List<String> toRemove = new java.util.ArrayList<>();

        for (Account acc : new java.util.ArrayList<>(accounts.values())) {
            acc.incrementMonths(months);

            for (int i = 0; i < months; i++) {
                if (acc instanceof CD) {
                    acc.applyMonthlyInterest();
                } else if (acc instanceof Savings) {
                    ((Savings) acc).applyMonthlyInterest();
                    if (acc.getBalance() < 1000) {
                        acc.withdraw(25);
                    }
                }
            }

            if (!(acc instanceof CD) && acc.getBalance() <= 0.01) {
                toRemove.add(acc.getId());
            }
        }

        for (String id : toRemove) {
            accounts.remove(id);
        }
    }

    public java.util.Collection<Account> getAccounts() {
        return accounts.values();
    }


}
