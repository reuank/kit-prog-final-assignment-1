package edu.kit.informatik.exceptions;

public class InvalidCallOfCommandException extends Exception {
    public InvalidCallOfCommandException() {

    }

    public InvalidCallOfCommandException(String exception) {
        super(exception);
    }
}