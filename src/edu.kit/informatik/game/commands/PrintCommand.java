package edu.kit.informatik.game.commands;

import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.serializers.ConnectSixSerializer;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IExecutableCommand;

/**
 * The executable implementation of the "print" command.
 * Builds a String representation of the whole game board.
 */
public class PrintCommand implements IExecutableCommand {
    private ConnectSix game;
    private CommandSignature commandSignature = new CommandSignature("print");

    /**
     * Instantiates a new Print command that contains all the methodology needed to execute the command.
     * @param game The game in which the command is valid.
     */
    public PrintCommand(ConnectSix game) {
        this.game = game;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            ConnectSixValidator.validateCommand(command, this.commandSignature);

            String gameBoardRepresentation = ConnectSixSerializer.serializeGameBoard(this.game.getGameBoard());

            outputStream.append(gameBoardRepresentation);
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
        return this.commandSignature.getArgName(index);
    }
}
