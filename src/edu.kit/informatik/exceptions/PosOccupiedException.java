package edu.kit.informatik.exceptions;

public class PosOccupiedException extends Exception {
    /**
     * Instantiates a Exceptions that shall be thrown if a position on the gameboard is already occupied.
     * @param exception The exception message.
     */
    public PosOccupiedException(String exception) {
        super(exception);
    }
}
