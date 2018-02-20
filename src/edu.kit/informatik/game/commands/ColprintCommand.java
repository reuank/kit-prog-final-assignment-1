package edu.kit.informatik.game.commands;

import edu.kit.informatik.constructs.program.Command;
import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.serializers.ColumnSerializer;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IExecutableCommand;

public class ColprintCommand implements IExecutableCommand {
    private ConnectSix game;
    private CommandSignature commandSignature = new CommandSignature("colprint col:int");

    /**
     * Instantiates a new Colprint command that contains all the methodology needed to execute the command.
     * @param game The game in which the command is valid.
     */
    public ColprintCommand(ConnectSix game) {
        this.game = game;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            ConnectSixValidator.validateCommand(command, this.commandSignature);

            int colId = Integer.parseInt(command.getArg(0));
            colId = game.convertCoordinate(colId, false);

            String colRepresentation = ColumnSerializer.serialize(game.getGameBoard(), colId);
            outputStream.append(colRepresentation);
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException("command \"" + command.getSlug() + "\" could not be executed."
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
        return null;
    }
}