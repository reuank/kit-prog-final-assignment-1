package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.userinterface.CLI;

public class QuitCommand extends UserInterfaceCommand implements ICommand {
    private CLI context;
    private CommandSignature commandSignature = new CommandSignature("quit");

    public QuitCommand(CLI context) {
        this.context = context;
    }

    public boolean tryToExecute(ICommand passedCommand, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            ConnectSixValidator.validateCommand(passedCommand, this.commandSignature);

            context.stop();

            return true;
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException("command \"" + passedCommand.getSlug() + "\" could not be executed."
                    + " The required structure is \"" + this.commandSignature.getCommandSignature() + "\", but "
                    + validationException.getMessage());
        }
    }

    public String getSlug() {
        return this.commandSignature.getSlug();
    }
}
