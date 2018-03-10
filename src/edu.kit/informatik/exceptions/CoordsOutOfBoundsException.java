package edu.kit.informatik.exceptions;

/**
 * The exception thrown if a given coordinate is not on the game board.
 */
public class CoordsOutOfBoundsException extends Exception {
    /**
     * Instantiates an exception that shall be thrown if a given coordinate is not on the game board.
     * @param exception The validation exception message, that gets appended to the standard error message.
     */
    public CoordsOutOfBoundsException(String exception) {
        super(String.format("the given coordinates are out of the bounds of the game boards for this game mode. "
                + "The %s", exception));
    }
}
