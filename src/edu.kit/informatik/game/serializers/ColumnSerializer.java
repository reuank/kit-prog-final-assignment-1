package edu.kit.informatik.game.serializers;

import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.game.board.GameBoard;

/**
 * Builds a string representation of a column of the game board.
 */
public class ColumnSerializer {
    /**
     * Used for building a String representation of a column of the game board.
     * @param gameBoard The game board you want to get the column from.
     * @param colId The id of the column that shall be serialized.
     * @return Return a String representation of the column.
     * @throws CoordsOutOfBoundsException Thrown if the column id is not on the game board.
     */
    public static String serialize(GameBoard gameBoard, int colId) throws CoordsOutOfBoundsException {
        StringBuilder colRepresentation = new StringBuilder();
        int[] col = gameBoard.getCol(colId);

        for (int i = 0; i < col.length; i++) {
            colRepresentation.append(StateSerializer.serialize(col[i]));
            colRepresentation.append(i < col.length - 1 ? " " : "");
        }

        return colRepresentation.toString();
    }
}