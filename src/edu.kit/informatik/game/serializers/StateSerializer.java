package edu.kit.informatik.game.serializers;

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
        return (state == 0 ? "**" : "P" + state);
    }
}