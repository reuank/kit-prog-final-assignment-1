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
    public void tryToExecute(ICommand passedCommand, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            ConnectSixValidator.validateCommand(passedCommand, this.commandSignature);

            int row = Integer.parseInt(passedCommand.getArg(0));
            int col = Integer.parseInt(passedCommand.getArg(1));
            Position point = game.getGameBoard().convertPosition(new Position(row, col));

            outputStream.append(StateSerializer.serialize(game.getState(point)));
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException("command \"" + passedCommand.getSlug() + "\" could not be executed."
                    + " The required structure is \"" + this.commandSignature.getCommandSignature() + "\", but "
                    + validationException.getMessage());
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