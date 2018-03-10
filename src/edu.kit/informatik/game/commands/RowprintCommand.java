package edu.kit.informatik.game.commands;

import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.serializers.ConnectSixSerializer;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IExecutableCommand;

/**
 * The executable implementation of the "rowprint" command.
 * Builds a String representation of a particular row of the game board.
 */
public class RowprintCommand implements IExecutableCommand {
    private ConnectSix game;
    private CommandSignature commandSignature = new CommandSignature("rowprint row:int");

    /**
     * Instantiates a new Rowprint command that contains all the methodology needed to execute the command.
     * @param game The game in which the command is valid.
     */
    public RowprintCommand(ConnectSix game) {
        this.game = game;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            ConnectSixValidator.validateCommand(command, this.commandSignature);

            // As the rowprint command is required to only accept values from 0 to N-1 (N = game board size),
            // the range restrictions are already applied here.
            int rowId = Integer.parseInt(command.getArg(0));
            rowId = game.convertCoordinate(rowId, false);

            String rowRepresentation = ConnectSixSerializer.serializeRow(game.getGameBoard(), rowId);
            outputStream.append(rowRepresentation);
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        } catch (CoordsOutOfBoundsException exception) {
            throw new InvalidCallOfCommandException(exception.getMessage());
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
