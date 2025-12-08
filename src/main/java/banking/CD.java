package banking;

public class CD extends Account {

    private final double initialBalance;

    public CD(String id, double apr, double initialBalance) {
        super(id, apr, initialBalance);
        this.initialBalance = initialBalance;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    @Override
    public void passTime(int months) {
        for (int i = 0; i < months; i++) {
            applyMonthlyInterest();
            incrementMonths(1);
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount >= balance)  {
            balance = 0;
            return;
        }
        balance -= amount;
    }
}
