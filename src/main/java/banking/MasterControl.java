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
        output.add(bank.getFormattedSummary().trim());

        for (String command : input) {
            command = command.trim();
            if (command.isEmpty()) continue;

            if (validator.validate(command)) {
                processor.process(command);
                if (!command.toLowerCase().startsWith("pass")) {
                    output.add(command);
                }
            } else {
                storage.addInvalidCommand(command);
            }

            String summary = bank.getFormattedSummary().trim();
            if (!summary.isEmpty()) {
                output.add(summary);
            }
        }
        return output;
    }

    public InvalidCommandStorage getInvalidCommandStorage() {
        return storage;
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        MasterControl mc = new MasterControl(bank);
    }
}