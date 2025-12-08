package banking;
public abstract class Account {
    private String id;
    private double apr;
    protected double balance;
    protected int monthsPassed = 0;

    public int getMonthsPassed() {
        return monthsPassed;
    }

    public void incrementMonths(int months) {
        this.monthsPassed += months;
    }

    public void applyMonthlyInterest() {
        double monthlyRate = apr / 100 / 12;
        double interest = balance * monthlyRate;
        balance += interest;
        balance = Math.round(balance * 100.0) / 100.0;
    }

    public Account(String id, double apr, double balance) {
        this.id = id;
        this.apr = apr;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public double getApr() {
        return apr;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
        if (balance < 0) {
            balance = 0;
        }
    }

    public boolean canWithdraw() {
        return true;
    }

    public void passTime(int months) {
        for (int i = 0; i < months; i++) {
            applyMonthlyInterest();
            incrementMonths(1);
        }
    }


}