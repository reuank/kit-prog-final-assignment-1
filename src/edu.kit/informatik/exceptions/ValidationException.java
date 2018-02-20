package edu.kit.informatik.exceptions;

public class ValidationException extends Exception {
    /**
     * Instantiates a Exceptions that shall be thrown if the validation process failed.
     * @param exception The exception message.
     */
    public ValidationException(String exception) {
        super(exception);
    }
}
