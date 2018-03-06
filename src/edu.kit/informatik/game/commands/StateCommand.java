package edu.kit.informatik.game.commands;

import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.serializers.StateSerializer;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IExecutableCommand;

/**
 * The executable implementation of the "state" command.
 * Builds a String representation of single position on the game board.
 */
public class StateCommand implements IExecutableCommand {
    private ConnectSix game;
    private CommandSignature commandSignature = new CommandSignature("state row:int;col:int");

    /**
     * Instantiates a new State command that contains all the methodology needed to execute the command.
     * @param game The game in which the command is valid.
     */
    public StateCommand(ConnectSix game) {
        this.game = game;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            ConnectSixValidator.validateCommand(command, this.commandSignature);

            int row = Integer.parseInt(command.getArg(0));
            int col = Integer.parseInt(command.getArg(1));
            Position point = game.getGameBoard().convertPosition(new Position(row, col));

            outputStream.append(StateSerializer.serialize(game.getState(point)));
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    String.format("command %s could not be executed. The required structure is %s, but %s",
                            command.getSlug(),
                            this.commandSignature.getCommandSignature(),
                            validationException.getMessage())
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