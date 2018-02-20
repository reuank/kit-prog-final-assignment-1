package edu.kit.informatik.exceptions;

public class CommandUndefinedException extends Exception {
    /**
     * Instantiates a Exceptions that shall be thrown if a undefined command was passed.
     * @param exception The exception message.
     */
    public CommandUndefinedException(String exception) {
        super(exception);
    }
}