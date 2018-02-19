package edu.kit.informatik.game.commands;

import edu.kit.informatik.constructs.list.PositionList;
import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.exceptions.PositionOccupiedException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import edu.kit.informatik.interfaces.ICommand;

public class PlaceCommand extends ConnectSixCommand implements ICommand {
    private ConnectSix game;
    private CommandSignature commandSignature = new CommandSignature("place row1:int;col1:int;row2:int;col2:int");

    public PlaceCommand(ConnectSix game) {
        this.game = game;
    }

    public boolean tryToExecute(ICommand passedCommand, StringBuilder outputStream) throws InvalidCallOfCommandException {
        try {
            // Check the passed command against the signature it should have
            ConnectSixValidator.validateCommand(passedCommand, this.commandSignature);

            if (!game.hasEnded()) {
                // If, and only if validation passed, convert the points and try to place them all
                PositionList moves = new PositionList();

                // Loop through the moves that were passed
                for (int i = 0; i < passedCommand.getArgs().length; i += 2) {
                    int row = Integer.parseInt(passedCommand.getArg(i));
                    int col = Integer.parseInt(passedCommand.getArg(i + 1));
                    moves.add(new Position(row, col));
                }

                game.tryPlaceMultiple(moves);
                game.checkConsequencesOfLastMoves();

                if (game.hasWinner()) {
                    outputStream.append(game.isDraw() ? "draw" : "P" + game.getWinner() + " wins");
                } else {
                    game.nextPlayer();
                    outputStream.append("OK");
                }

                return true;
            } else {
                throw new InvalidCallOfCommandException("the game has already ended!");
            }
        } catch (ValidationException validationException) {
            throw new InvalidCallOfCommandException("command \"" + passedCommand.getSlug() + "\" could not be executed."
                    + " The required structure is \"" + this.commandSignature.getCommandSignature() + "\", but "
                    + validationException.getMessage());
        } catch (PositionOccupiedException | CoordinatesOutOfBoundsException exception) {
            throw new InvalidCallOfCommandException(exception.getMessage());
        }
    }

    public String getSlug() {
        return this.commandSignature.getSlug();
    }
}