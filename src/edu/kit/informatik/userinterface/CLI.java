package edu.kit.informatik.userinterface;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.constructs.list.KeyValueList;
import edu.kit.informatik.constructs.databind.KeyValuePair;
import edu.kit.informatik.exceptions.*;
import edu.kit.informatik.constructs.program.Command;
import edu.kit.informatik.game.parser.ConnectSixParser;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IUserInterface;
import edu.kit.informatik.validation.SyntaxValidator;

public class CLI implements IUserInterface {
    private ConnectSixParser parser;
    private KeyValueList<String, ICommand> commandRegister;
    private boolean isRunning;

    public CLI(ConnectSixParser parser) {
        this.parser = parser;
        commandRegister = new KeyValueList<>();
    }

    public void registerCommands(ICommand[] commands) {
        for (ICommand command : commands) {
            this.registerCommand(command);
        }
    }

    private void registerCommand(ICommand command) {
        KeyValuePair<String, ICommand> newCommandRegisterEntry = new KeyValuePair<>(command.getSlug(), command);
        commandRegister.add(newCommandRegisterEntry);
    }

    public void init() {
        this.start();
        while (isRunning) {
            try {
                ICommand inputCommand = parser.parseCommand(input());
                process(inputCommand);
            } catch (ParserException | CommandUndefinedException | InvalidCallOfCommandException exception) {
                this.outputError(exception.getMessage());
            }
        }
    }

    private boolean commandIsRegistered(ICommand command) {
        return commandRegister.containsKey(command.getSlug());
    }

    private void process(ICommand passedCommand) throws CommandUndefinedException, InvalidCallOfCommandException {
        if (commandIsRegistered(passedCommand)) {
            ICommand correspondingCommand = commandRegister.getValueByKey(passedCommand.getSlug());

            StringBuilder outputStream = new StringBuilder();
            correspondingCommand.tryToExecute(passedCommand, outputStream);

            flushOutput(outputStream); // Show all messages that have been generated within the command
        } else {
            throw new CommandUndefinedException("the command \"" + passedCommand.getSlug() + "\" is not defined.");
        }
    }

    public ConnectSixParser getParser() {
        return parser;
    }

    private void flushOutput(StringBuilder outputStream) {
        if (!outputStream.toString().equals("")) {
            output(outputStream.toString());
        }
    }

    public void start() {
        this.isRunning = true;
    }

    public void stop() {
        this.isRunning = false;
    }

    public void output(String message) {
        Terminal.printLine(message);
    }

    public void outputError(String message) {
        Terminal.printError(message);
    }

    public String input() {
        return Terminal.readLine();
    }
}
