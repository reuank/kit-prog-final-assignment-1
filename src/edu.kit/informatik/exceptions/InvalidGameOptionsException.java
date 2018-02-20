package edu.kit.informatik.exceptions;

public class InvalidGameOptionsException extends Exception {
    /**
     * Instantiates a Exceptions that shall be thrown if the provided game options are invalid or empty.
     * @param exception The exception message.
     */
    public InvalidGameOptionsException(String exception) {
        super(exception);
    }
}