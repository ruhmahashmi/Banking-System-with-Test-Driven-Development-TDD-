package banking;

public class WithdrawCommandValidator extends CommandValidator {
    public WithdrawCommandValidator(Bank bank) { super(bank); }
    @Override public boolean validate(String c) {
        String[] p = c.split("\\s+");
        if (p.length != 3 || !p[0].equalsIgnoreCase("withdraw")) return false;
        if (!p[1].matches("\\d{8}")) return false;
        if (!bank.accountExists(p[1])) return false;
        try { double amt = Double.parseDouble(p[2]); return amt > 0; }
        catch (Exception e) { return false; }
        return true;
    }
}