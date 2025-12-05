package banking;

public class PassTimeCommandValidator extends CommandValidator {
    public PassTimeCommandValidator() { super(null); }
    @Override public boolean validate(String c) {
        String[] p = c.split("\\s+");
        if (p.length != 2 || !p[0].equalsIgnoreCase("pass")) return false;
        try {
            int m = Integer.parseInt(p[1]);
            return m >= 1 && m <= 60;
        } catch (Exception e) { return false; }
        return true;
    }
}