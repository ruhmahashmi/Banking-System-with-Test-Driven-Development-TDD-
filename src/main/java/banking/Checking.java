package banking;

public class Checking extends Account {

    public Checking(String id, double apr) {
        super(id, apr, 0.0);
    }

    @Override
    public void passTime(int months) {
        for (int i = 0; i < months; i++) {
            applyMonthlyInterest();
            incrementMonths(1);
        }
    }
}
