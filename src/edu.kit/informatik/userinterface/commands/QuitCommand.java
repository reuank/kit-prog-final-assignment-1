package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IExecutableCommand;
import edu.kit.informatik.userinterface.CLI;

/**
 *
 */
public class QuitCommand implements IExecutableCommand {
    private CLI userInterface;
    private CommandSignature commandSignature = new CommandSignature("quit");

    /**
     * Instantiates a new Quit command that quits the game.
     * @param userInterface The userInterface in which the command is valid.
     */
    public QuitCommand(CLI userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            ConnectSixValidator.validateCommand(command, this.commandSignature);

            userInterface.stop();
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        }
    }

    @Override
    public String getSlug() {
        return this.commandSignature.getSlug();
    }

    @Override
    public String[] getArgs() {
        return this.commandSignature.getArgNames();
    }

    @Override
    public String getArg(int index) {
        return null;
    }
}
