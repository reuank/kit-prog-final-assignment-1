package edu.kit.informatik.game.serializers;

import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;
import edu.kit.informatik.game.board.GameBoard;

public class RowSerializer {
    public static String serialize(GameBoard gameBoard, int rowNumber) throws CoordinatesOutOfBoundsException {
        StringBuilder rowRepresentation = new StringBuilder();
        int[] col = gameBoard.getRow(rowNumber);
        for (int i = 0; i < col.length; i++) {
            rowRepresentation.append(col[i] == 0 ? "**" : "P" + col[i]);
            rowRepresentation.append(i < col.length - 1 ? " " : "");
        }
        return rowRepresentation.toString();
    }
}
