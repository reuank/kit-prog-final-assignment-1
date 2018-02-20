package edu.kit.informatik.exceptions;

public class InvalidCallOfCommandException extends Exception {
    /**
     * Instantiates a Exceptions that shall be thrown if a command was called without correct and executable parameters.
     * @param exception The exception message.
     */
    public InvalidCallOfCommandException(String exception) {
        super(exception);
    }
}