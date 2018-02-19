package edu.kit.informatik.game.commands;

import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;

public class ResetCommand extends ConnectSixCommand implements ICommand {
    private ConnectSix game;
    private CommandSignature commandSignature = new CommandSignature("reset");

    public ResetCommand(ConnectSix game) {
        this.game = game;
    }

    public boolean tryToExecute(ICommand passedCommand, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            ConnectSixValidator.validateCommand(passedCommand, this.commandSignature);

            this.game.reset();
            outputStream.append("OK");

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
