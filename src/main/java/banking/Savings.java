package banking;

public class Savings extends Account {
    public Savings(String id, double apr) {
        super(id, apr, 0.0);
    }

    @Override
    public void passTime(int months) {
        for (int m = 0; m < months; m++) {
            applyMonthlyInterest();

            if (getBalance() < 1000) {
                withdraw(25);
            }

            incrementMonths(1);
        }
    }
}
