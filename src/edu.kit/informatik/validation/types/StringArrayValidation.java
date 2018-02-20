package edu.kit.informatik.validation.types;

import edu.kit.informatik.constructs.program.ValidationResult;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.validation.SyntaxValidator;

public class StringArrayValidation {

    private String[] validateMe;
    private ValidationResult validationResult;

    public StringArrayValidation(String[] validateMe) {
        this.validateMe = validateMe;
        this.validationResult = new ValidationResult();
    }

    public StringArrayValidation isExactly(String[] twin) {
        if (!this.validateMe.equals(twin)) {
            return addError("should be exactly " + twin);
        }

        return this;
    }

    public StringArrayValidation isNotNull() {
        if (validateMe == null) {
            return addError("should not be null");
        }

        return this;
    }

    public StringArrayValidation isNotEmpty() {
        for (int i = 0; i < validateMe.length; i++) {
            if (!validateMe[0].equals("")) { // Found something!
                return this;
            }
        }

        return addError("should not be empty");
    }

    public StringArrayValidation isLongerOrEqualThan(int length) {
        if (validateMe == null) {
            return addError("should not be null in order to be checked whether it is longer than " + length);
        }

        if (validateMe.length <= length) {
            return addError("should be longer than " + length);
        }

        return this;
    }

    public StringArrayValidation isShorterOrEqualThan(int length) {
        if (validateMe.length >= length) {
            return addError("should be shorter than " + length);
        }

        return this;
    }

    public StringArrayValidation isOfLength(int length) {
        if (validateMe == null) {
            if (length == 0) {
                return this;
            } else {
                return addError("has length 0 instead of " + length);
            }
        } else if (validateMe.length != length ) {
            return addError("has length " + validateMe.length + " instead of " + length);
        }

        return this;
    }

    public StringArrayValidation isOfLengthBetween(int lowerBound, int upperBound) {
        if (validateMe == null && lowerBound == 0 && upperBound == 0 ) {
            return this;
        }

        if (validateMe.length < lowerBound || validateMe.length > upperBound) {
            return addError("should have length between " + lowerBound + " and " + upperBound);
        }

        return this;
    }

    public StringArrayValidation isContaining(String needle) throws ValidationException {
        if (validateMe == null) {
            return addError("should not be null to be searched for \"" + needle + "\"");
        }

        for (String value : this.validateMe) {
            if (value.equals(needle)) {
                return this;
            }
        }

        return addError("should contain value " + needle);
    }

    /**
     * This method can be used to ensure that the values in a string array can be converted to a specific datatype.
     * Currently the following datatype-checks are supported: int
     * In the future, there can be added float-checks, regex checks or anything alike.
     * @param argTypes This is the list of datatypes that validateMe should be checked against
     * @return Returns the current Validation object, so that other validation can be applied
     * @throws ValidationException If the check fails or the input parameters are false, an exception will be thrown.
     */
    public StringArrayValidation checkTypes(String[] argTypes) throws ValidationException {
        // Ensure that there is something to check
        if (validateMe == null) {
            // No need to check anything
            if (argTypes == null) {
                return this;
            }

            return addError("validation failed while trying to check the types in the passed arguments list");
        }

        // Number of types to check not equals number of given types
        if (validateMe.length != argTypes.length) {
            return addError("validation failed while trying to check the types in the passed arguments list. "
                    + "The number of types to check (" + argTypes.length + ") does not match "
                    + "the number of available arguments (" + validateMe.length + ").");
        }

        StringBuilder failedIntParams = new StringBuilder();

        for (int i = 0; i < this.validateMe.length; i++) {
            switch (argTypes[i]) {
                case "int":
                    if (SyntaxValidator.validateInt(validateMe[i]).hasFailed()) {
                        failedIntParams.append((i + 1));
                        failedIntParams.append(i < this.validateMe.length - 1 ? " and " : ""); // Add "and" separation
                    }
                    break;
                default:
                    throw new ValidationException("there is an undefined datatype in the command signature. Check your code!");
            }
        }

        String failedInts = failedIntParams.toString();
        addError(!failedInts.equals("") ? "number " + failedIntParams.toString() + " is not an integer" : "");

        return this;
    }

    public StringArrayValidation throwIfInvalid(String paramName) throws ValidationException {
        if (this.validationResult.failed()) {
            throw new ValidationException(paramName + " " + validationResult.getMessage() + ".");
        }

        return this;
    }

    public boolean hasFailed() {
        return this.validationResult.failed();
    }


    public String getErrors(String paramName) {
        return this.validationResult.getMessage();
    }

    private StringArrayValidation addError(String error) {
        if (error == null || error.equals("")) { // No errors to be added. Empty call.
            return this;
        }

        validationResult.addValidationError(error);
        return this;
    }

    public String[] getResult() {
        return this.validateMe;
    }
}
