package edu.kit.informatik.exceptions;

public class InvalidGameOptionsException extends Exception {
    public InvalidGameOptionsException() {

    }

    public InvalidGameOptionsException(String exception) {
        super(exception);
    }
}