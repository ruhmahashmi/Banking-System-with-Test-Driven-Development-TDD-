package banking;

public class MasterControl {
    private final CommandValidator validator;
    private final CommandProcessor processor;
    private final InvalidCommandStorage storage;

    public MasterControl(CommandValidator validator,
                         CommandProcessor processor,
                         InvalidCommandStorage storage) {
        this.validator = validator;
        this.processor = processor;
        this.storage = storage;
    }

    public void executeCommand(String command) {
        if (validator.validate(command)) {
            processor.process(command);
        } else {
            storage.addInvalidCommand(command);
        }
    }

    public InvalidCommandStorage getInvalidCommandStorage() {
        return storage;
    }
}