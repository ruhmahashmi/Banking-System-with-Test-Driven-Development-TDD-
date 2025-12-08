package banking;

public class WithdrawCommandValidator extends CommandValidator {
    public WithdrawCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] p = command.trim().split("\\s+");
        if (p.length != 3 || !p[0].equalsIgnoreCase("withdraw")) {
            return false;
        }

        if (!p[1].matches("\\d{8}") || !bank.accountExists(p[1])) {
            return false;
        }

        Account acc = bank.getAccount(p[1]);
        if (acc instanceof CD && acc.getMonthsPassed() < 12) {
            return false;
        }

        return isValidWithdrawAmount(acc, p[2]);
    }

    private boolean isValidWithdrawAmount(Account acc, String amountStr) {
        try {
            double amt = Double.parseDouble(amountStr);
            if (amt <= 0) return false;
            if (acc instanceof Checking && amt > 400) return false;
            if (acc instanceof Savings && amt > 1000) return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
