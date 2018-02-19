package edu.kit.informatik.constructs.program;

public class ValidationResult {
    private boolean failed;
    private StringBuilder message;

    public ValidationResult() {
        this.failed = false;
        this.message = new StringBuilder();
    }

    public void addValidationError(String message) {
        this.failed = true;
        this.message.append(this.message.toString().equals("") ? message : " and " + message);
    }

    public boolean failed() {
        return this.failed;
    }

    public String getMessage() {
        return message.toString();
    }
}