package edu.kit.informatik.game.serializers;

import edu.kit.informatik.constructs.specific.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.game.board.GameBoard;
import edu.kit.informatik.game.serializers.types.ColumnSerializer;
import edu.kit.informatik.game.serializers.types.GameBoardSerializer;
import edu.kit.informatik.game.serializers.types.RowSerializer;
import edu.kit.informatik.game.serializers.types.StateSerializer;

/**
 * The class that manages all the available serializers.
 * This way all the available serializer can be accessed via Intellisense by typing "ConnectSixSerializer."
 */
public class ConnectSixSerializer {
    /**
     * Used for building a String representation of a field on the game board.
     * @param gameBoard The gameBoard with all the states.
     * @param position The position of the state of interest.
     * @return Returns the converted state as a String.
     * @throws CoordsOutOfBoundsException Thrown if the position is not in the bounds of the board.
     */
    public static String serializeState(GameBoard gameBoard, Position position) throws CoordsOutOfBoundsException {
        return StateSerializer.serialize(gameBoard, position);
    }

    /**
     * Used for building a String representation of a row of the game board.
     * @param gameBoard The game board you want to get the row from.
     * @param rowId The id of the row that shall be serialized.
     * @return Return a String representation of the row.
     * @throws CoordsOutOfBoundsException Thrown if the row id is not on the game board.
     */
    public static String serializeRow(GameBoard gameBoard, int rowId) throws CoordsOutOfBoundsException {
        return RowSerializer.serialize(gameBoard, rowId);
    }

    /**
     * Used for building a String representation of a column of the game board.
     * @param gameBoard The game board you want to get the column from.
     * @param colId The id of the column that shall be serialized.
     * @return Return a String representation of the column.
     * @throws CoordsOutOfBoundsException Thrown if the column id is not on the game board.
     */
    public static String serializeCol(GameBoard gameBoard, int colId) throws CoordsOutOfBoundsException {
        return ColumnSerializer.serialize(gameBoard, colId);
    }

    /**
     * Used for building a String representation of a whole game board.
     * @param gameBoard The game board that shall be serialized.
     * @return Return the String representation of the given game board.
     */
    public static String serializeGameBoard(GameBoard gameBoard) {
        return GameBoardSerializer.serialize(gameBoard);
    }
}