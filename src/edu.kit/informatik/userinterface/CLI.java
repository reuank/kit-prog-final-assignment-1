package edu.kit.informatik.userinterface;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.constructs.databind.KeyValuePair;
import edu.kit.informatik.constructs.list.KeyValueList;
import edu.kit.informatik.exceptions.CommandUndefinedException;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.ParserException;
import edu.kit.informatik.game.parser.ConnectSixParser;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IExecutableCommand;
import edu.kit.informatik.interfaces.IUserInterface;

/**
 * The Command Line Interface used for handling all user interactions and outputs.
 * Here all the commands are registered and managed. All custom exceptions thrown in the stack are passed to this class
 * and the errors will be printed on the command line.
 */
public class CLI implements IUserInterface {
    private ConnectSixParser parser;
    private KeyValueList<String, IExecutableCommand> commandRegister;
    private boolean isRunning;

    /**
     * Creates a new Command Line Interface.
     * @param parser The injected parser that shall be used by the interface.
     */
    public CLI(ConnectSixParser parser) {
        this.parser = parser;
        this.commandRegister = new KeyValueList<>();
    }

    @Override
    public void registerCommands(IExecutableCommand[] commands) {
        for (IExecutableCommand command : commands) {
            this.registerCommand(command);
        }
    }

    /**
     * Registers a single command.
     * @param cmd The executable command that shall be registered.
     */
    private void registerCommand(IExecutableCommand cmd) {
        KeyValuePair<String, IExecutableCommand> newCommandRegisterEntry = new KeyValuePair<>(cmd.getSlug(), cmd);
        this.commandRegister.add(newCommandRegisterEntry);
    }

    @Override
    public void run() {
        this.start();

        while (this.isRunning) {
            try {
                ICommand inputCommand = this.parser.parseCommand(input());
                process(inputCommand);
            } catch (ParserException | CommandUndefinedException | InvalidCallOfCommandException exception) {
                this.outputError(exception.getMessage());
            }
        }
    }

    @Override
    public void start() {
        this.isRunning = true;
    }

    @Override
    public void stop() {
        this.isRunning = false;
    }

    /**
     * Processes a command, which means passing the command data to the corresponding executable command class.
     * @param passedCommand The command that shall be executed.
     * @throws CommandUndefinedException Thrown if the command is not registered.
     * @throws InvalidCallOfCommandException Thrown if anything went wrong during the execution of the command.
     */
    private void process(ICommand passedCommand) throws CommandUndefinedException, InvalidCallOfCommandException {
        if (commandIsRegistered(passedCommand)) {
            // Lookup of the executable command that belongs to the passed command.
            IExecutableCommand correspondingCommand = this.commandRegister.getValueByKey(passedCommand.getSlug());

            // Pass the passed command over to the corresponding executable command
            // for further validation and final execution.
            StringBuilder outputStream = new StringBuilder();
            correspondingCommand.tryToExecute(passedCommand, outputStream);

            // Show all messages that have been generated within the command
            flushOutput(outputStream);
        } else {
            throw new CommandUndefinedException("the command \"" + passedCommand.getSlug() + "\" is not defined.");
        }
    }

    /**
     * Checks whether a particular command (a command slug respectively) is registered.
     * @param command The command that should be searched for.
     * @return Returns true if the command is registered.
     */
    private boolean commandIsRegistered(ICommand command) {
        return this.commandRegister.containsKey(command.getSlug());
    }

    /**
     * Returns the parser that belongs to the Interface.
     * @return The parser of this interface.
     */
    public ConnectSixParser getParser() {
        return this.parser;
    }

    /**
     * Used for displaying all the messages that have been generated within a command.
     * @param outputStream The output stream that has been passed to the command before.
     */
    private void flushOutput(StringBuilder outputStream) {
        if (!outputStream.toString().equals("")) {
            output(outputStream.toString());
        }
    }

    /**
     * Used for outputting messages via the command line.
     * @param message The message that shall be printed.
     */
    private void output(String message) {
        Terminal.printLine(message);
    }

    /**
     * Used for outputting errors via the command line.
     * @param message The error that shall be printed.
     */
    public void outputError(String message) {
        Terminal.printError(message);
    }

    /**
     * Used for reading input via the command line.
     * @return Returns the String value that has been inputted.
     */
    private String input() {
        return Terminal.readLine();
    }
}