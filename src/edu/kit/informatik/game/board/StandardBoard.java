package edu.kit.informatik.game.board;

import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;
import edu.kit.informatik.exceptions.PositionOccupiedException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.validation.SyntaxValidator;

public class StandardBoard extends GameBoard {
    public StandardBoard(GameOptions gameOptions) {
        this.field = new int[gameOptions.getFieldSize()][gameOptions.getFieldSize()];
    }

    public Position convertPosition(Position point) throws CoordinatesOutOfBoundsException {
        try {
            SyntaxValidator.validateInt(point.getRow())
                    .isInRange(0, field.length - 1)
                    .throwIfInvalid("row");

            SyntaxValidator.validateInt(point.getCol())
                    .isInRange(0, field.length - 1)
                    .throwIfInvalid("column");

            return point;
        } catch (ValidationException validationException) {
            throw new CoordinatesOutOfBoundsException("the given coordinates are out of the bounds for this game mode."
                    + " The " + validationException.getMessage());
        }
    }

    public boolean isWall(Position position) {
        try {
            convertPosition(position);
            return false;
        } catch (CoordinatesOutOfBoundsException exception) {
            return true;
        }
    }
}
