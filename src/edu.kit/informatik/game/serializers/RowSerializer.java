package edu.kit.informatik.game.serializers;

import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.game.board.GameBoard;

/**
 * Builds a string representation of a row of the game board.
 */
public class RowSerializer {
    /**
     * Used for building a String representation of a row of the game board.
     * @param gameBoard The game board you want to get the row from.
     * @param rowId The id of the row that shall be serialized.
     * @return Return a String representation of the row.
     * @throws CoordsOutOfBoundsException Thrown if the row id is not on the game board.
     */
    public static String serialize(GameBoard gameBoard, int rowId) throws CoordsOutOfBoundsException {
        StringBuilder rowRepresentation = new StringBuilder();
        int[] row = gameBoard.getRow(rowId);

        for (int i = 0; i < row.length; i++) {
            rowRepresentation.append(StateSerializer.serialize(row[i]));
            rowRepresentation.append(i < row.length - 1 ? " " : "");
        }

        return rowRepresentation.toString();
    }
}