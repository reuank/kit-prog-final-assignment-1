package edu.kit.informatik.game.commands;

import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.serializers.StateSerializer;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;

public class StateCommand extends ConnectSixCommand implements ICommand {
    private ConnectSix game;
    private CommandSignature commandSignature = new CommandSignature("state y:int;x:int");

    public StateCommand(ConnectSix game) {
        this.game = game;
    }

    public boolean tryToExecute(ICommand passedCommand, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            ConnectSixValidator.validateCommand(passedCommand, this.commandSignature);

            int y = Integer.parseInt(passedCommand.getArgs()[0]);
            int x = Integer.parseInt(passedCommand.getArgs()[1]);
            Position point = new Position(x, y);

            outputStream.append(StateSerializer.serialize(game.getState(point)));

            return true;
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException("command \"" + passedCommand.getSlug() + "\" could not be executed."
                    + " The required structure is \"" + this.commandSignature.getCommandSignature() + "\", but "
                    + validationException.getMessage());
        } catch (CoordinatesOutOfBoundsException exception) {
            throw new InvalidCallOfCommandException(exception.getMessage());
        }
    }

    public String getSlug() {
        return this.commandSignature.getSlug();
    }
}