package edu.kit.informatik.game.serializers.types;

import edu.kit.informatik.constructs.specific.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.game.board.GameBoard;

/**
 * Builds a string representation of a position on the game board.
 */
public class StateSerializer {
    /**
     * Used for building a String representation of a field on the game board.
     * @param state The state that shall be serialized.
     * @return Returns the converted state as a String.
     */
    public static String serialize(int state) {
        return (state == 0) ? "**" : "P" + state;
    }

    /**
     * Used for building a String representation of a field on the game board.
     * @param gameBoard The gameBoard with all the states.
     * @param position The position of the state of interest.
     * @return Returns the converted state as a String.
     * @throws CoordsOutOfBoundsException Thrown if the position is not in the bounds of the board.
     */
    public static String serialize(GameBoard gameBoard, Position position) throws CoordsOutOfBoundsException {
        int state = gameBoard.getState(position);
        return serialize(state);
    }
}