package edu.kit.informatik.game.commands;

import edu.kit.informatik.constructs.list.List;
import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.constructs.specific.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.PosOccupiedException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IExecutableCommand;

/**
 * The executable implementation of the "place" command.
 * Validates and passes moves to the game.
 */
public class PlaceCommand implements IExecutableCommand {
    private ConnectSix game;
    private CommandSignature commandSignature = new CommandSignature("place row1:int;col1:int;row2:int;col2:int");

    /**
     * Instantiates a new Place command that contains all the methodology needed to execute the command.
     * @param game The game in which the command is valid.
     */
    public PlaceCommand(ConnectSix game) {
        this.game = game;
    }

    @Override
    public void tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            // Check the passed command against the signature it should have
            ConnectSixValidator.validateCommand(command, this.commandSignature);

            if (this.game.hasEnded()) {
                throw new InvalidCallOfCommandException("the game has already ended!");
            }

            // If, and only if validation passed, convert the points and try to place them all
            List<Position> moves = new List<>();

            // Loop through the moves that were passed
            for (int i = 0; i < command.getArgs().length; i += 2) {
                int row = Integer.parseInt(command.getArg(i));
                int col = Integer.parseInt(command.getArg(i + 1));
                moves.add(new Position(row, col));
            }

            // Try to place all the passed moves
            this.game.tryPlaceMultiple(moves);

            // Check if the game is still running after the moves have been made
            if (this.game.hasEnded()) {
                outputStream.append(this.game.isDraw() ? "draw" : "P" + this.game.getWinner() + " wins");
            } else {
                this.game.nextPlayer();
                outputStream.append("OK");
            }
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException(
                    command.getSlug(),
                    this.commandSignature.getCommandSignature(),
                    validationException.getMessage()
            );
        } catch (PosOccupiedException | CoordsOutOfBoundsException exception) {
            game.undoLastMoves();
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