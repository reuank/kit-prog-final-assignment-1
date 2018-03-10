package edu.kit.informatik.exceptions;

/**
 * The exception thrown if a position on the gameboard is already occupied.
 */
public class PosOccupiedException extends Exception {
    /**
     * Instantiates an exception that shall be thrown if a position on the gameboard is already occupied.
     * @param row The row of the occupied position.
     * @param col The column of the occupied position.
     * @param player The player that has already occupied the position.
     */
    public PosOccupiedException(int row, int col, int player) {
        super(String.format("the field %d;%d is already occupied by Player %d.", row, col, player));
    }

    /**
     * Instantiates an exception that shall be thrown if a position on the gameboard is already occupied.
     * @param exception The custom exception message.
     */
    public PosOccupiedException(String exception) {
        super(exception);
    }
}