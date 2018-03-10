package edu.kit.informatik.exceptions;

/**
 * The exception thrown if the provided game options are invalid or empty.
 */
public class InvalidGameOptionsException extends Exception {
    /**
     * Instantiates an exception that shall be thrown if the provided game options are invalid or empty.
     * @param exception The exception message.
     */
    public InvalidGameOptionsException(String exception) {
        super(exception);
    }
}