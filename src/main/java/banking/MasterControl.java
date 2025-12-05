// src/main/java/banking/MasterControl.java
package banking;

import java.util.Scanner;

public class MasterControl {
    private final CommandValidator validator;
    private final CommandProcessor processor;
    private final InvalidCommandStorage storage;
    private final Bank bank;

    public MasterControl(Bank bank, InvalidCommandStorage storage) {
        this.bank = bank;
        this.storage = storage;
        this.validator = new CommandValidator(bank);
        this.processor = new CommandProcessor(bank, storage);
    }

    public void executeCommand(String command) {
        if (validator.validate(command)) {
            processor.process(command);
            System.out.println(bank.getFormattedSummary());
            System.out.println();
        } else {
            storage.addInvalidCommand(command);
        }
    }

    public InvalidCommandStorage getInvalidCommandStorage() {
        return storage;
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        InvalidCommandStorage storage = new InvalidCommandStorage();
        MasterControl masterControl = new MasterControl(bank, storage);

        Scanner scanner = new Scanner(System.in);
        System.out.println(bank.getFormattedSummary());
        System.out.println();

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("quit")) break;
            masterControl.executeCommand(command);
        }
    }
}