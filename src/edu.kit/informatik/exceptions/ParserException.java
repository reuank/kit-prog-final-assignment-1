package edu.kit.informatik.exceptions;

/**
 * The exception thrown if the passed input data could not be parsed to a specific object.
 */
public class ParserException extends Exception {
    /**
     * Instantiates a Exceptions that shall be thrown if the passed input data could not be parsed to a specific object.
     * @param exception The exception message.
     */
    public ParserException(String exception) {
        super(exception);
    }
}
