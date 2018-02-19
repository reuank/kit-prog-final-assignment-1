package edu.kit.informatik.game.serializers;

import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;
import edu.kit.informatik.game.board.GameBoard;

public class ColumnSerializer {
    public static String serialize(GameBoard gameBoard, int colNumber) throws CoordinatesOutOfBoundsException {
        StringBuilder colRepresentation = new StringBuilder();
        int[] col = gameBoard.getCol(colNumber);
        for (int i = 0; i < col.length; i++) {
            colRepresentation.append(col[i] == 0 ? "**" : "P" + col[i]);
            colRepresentation.append(i < col.length - 1 ? " " : "");
        }
        return colRepresentation.toString();
    }
}
