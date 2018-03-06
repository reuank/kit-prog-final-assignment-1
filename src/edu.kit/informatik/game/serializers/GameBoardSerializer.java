package edu.kit.informatik.game.serializers;

import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.game.board.GameBoard;

/**
 * Builds a string representation of a whole game board.
 */
public class GameBoardSerializer {
    /**
     * Used for building a String representation of a whole game board.
     * @param gameBoard The game board that shall be serialized.
     * @return Return the String representation of the given game board.
     */
    public static String serialize(GameBoard gameBoard) {
        StringBuilder gameBoardRepresentation = new StringBuilder();

        try {
            for (int i = 0; i < gameBoard.getFieldSize(); i++) { // Loop through the rows of the game board.
                gameBoardRepresentation.append(RowSerializer.serialize(gameBoard, i));
                gameBoardRepresentation.append(i < gameBoard.getFieldSize() - 1 ? "\n" : "");
            }

            return gameBoardRepresentation.toString();
        } catch (CoordsOutOfBoundsException exception) {
            throw new IllegalStateException(); // should never be reached, as the coordinates never leave the board.
        }
    }
}