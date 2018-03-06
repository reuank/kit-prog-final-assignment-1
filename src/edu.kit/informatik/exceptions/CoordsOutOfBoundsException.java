package edu.kit.informatik.exceptions;

/**
 * The exception thrown if a given coordinate is not on the game board.
 */
public class CoordsOutOfBoundsException extends Exception {
    /**
     * Instantiates a Exceptions that shall be thrown if a given coordinate is not on the game board.
     * @param exception The exception message.
     */
    public CoordsOutOfBoundsException(String exception) {
        super(exception);
    }
}
