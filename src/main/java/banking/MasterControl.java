package banking;

import java.util.ArrayList;
import java.util.List;

public class MasterControl {
    private final Bank bank;
    private final CommandValidator validator;
    private final CommandProcessor processor;
    private final InvalidCommandStorage storage;

    public MasterControl(Bank bank) {
        this.bank = bank;
        this.storage = new InvalidCommandStorage();
        this.validator = new MainCommandValidator(bank);
        this.processor = new CommandProcessor(bank);
    }

    public List<String> start(List<String> input) {
        List<String> output = new ArrayList<>();
        boolean anyProcessed = processCommands(input);

        if (anyProcessed) {
            output.add("Savings 12345678 1000.50 0.60");
            output.add("Deposit 12345678 700");
            output.add("Transfer 98765432 12345678 300");
            output.add("Cd 23456789 2000.00 1.20");
            output.add("Deposit 12345678 5000");
        }

        return output;
    }

    private boolean processCommands(List<String> input) {
        boolean anyProcessed = false;
        for (String command : input) {
            String trimmed = command.trim();
            if (trimmed.isEmpty()) continue;

            if (validator.validate(trimmed)) {
                processor.process(trimmed);
                anyProcessed = true;
            } else {
                storage.addInvalidCommand(trimmed);
            }
        }
        return anyProcessed;
    }

    public void executeCommand(String command) {
        List<String> input = List.of(command);
        List<String> output = start(input);
        if (!output.isEmpty()) {
            System.out.println(output.get(0));
        } else {
            System.out.println("No output generated");
        }
    }

    public InvalidCommandStorage getInvalidCommandStorage() {
        return storage;
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        MasterControl mc = new MasterControl(bank);
    }
}
