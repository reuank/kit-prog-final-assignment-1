package edu.kit.informatik.validation.types;

import edu.kit.informatik.constructs.program.ValidationResult;
import edu.kit.informatik.exceptions.ValidationException;

public class StringValidation { //implements IValidation<String>{
    private String validateMe;
    private ValidationResult validationResult;

    public StringValidation(String validateMe) {
        this.validateMe = validateMe;
        this.validationResult = new ValidationResult();
    }

    public StringValidation isExactly(String twin) throws ValidationException {
        if ( !this.validateMe.equals(twin)) {
            return addError("should be exactly " + twin);
        }

        return this;
    }

    public StringValidation isNotNull() throws ValidationException {
        if (validateMe == null) {
            return addError("should not be null.");
        }

        return this;
    }

    public StringValidation isInSet(String[] set) throws ValidationException {
        StringBuilder setRepresentation = new StringBuilder();
        for (int i = 0; i < set.length; i++) {
            setRepresentation.append("\"" + set[i] + "\"");
            setRepresentation.append(i < set.length - 1 ? " or " : "");

            if (set[i].equals(validateMe)) {
                return this;
            }
        }

        return addError("should be either " + setRepresentation.toString() + ".");
    }

    public StringValidation throwIfInvalid(String paramName) throws ValidationException {
        if (this.validationResult.failed()) {
            throw new ValidationException(paramName + " " + validationResult.getMessage() + ".");
        }

        return this;
    }

    public String getErrors(String paramName) {
        return this.validationResult.getMessage();
    }

    public boolean hasFailed() {
        return this.validationResult.failed();
    }


    private StringValidation addError(String error) throws ValidationException {
        validationResult.addValidationError(error);
        return this;
    }

    public String getResult() {
        return this.validateMe;
    }
}