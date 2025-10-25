public abstract class Account {
    private String id;
    private double apr;
    protected double balance;

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
        if (amount > balance) {
            balance = 0;
        } else {
            balance -= amount;
        }
    }
}