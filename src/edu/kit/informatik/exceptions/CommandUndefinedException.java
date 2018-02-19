package edu.kit.informatik.exceptions;

public class CommandUndefinedException extends Exception {
    public CommandUndefinedException() {

    }

    public CommandUndefinedException(String exception) {
        super(exception);
    }
}
