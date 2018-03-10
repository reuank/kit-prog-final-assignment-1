package edu.kit.informatik.validation.types;

import edu.kit.informatik.constructs.program.ValidationResult;
import edu.kit.informatik.exceptions.ValidationException;

/**
 * This class holds all applicable String validations.
 */
public class StringValidation {
    private String validateMe;
    private ValidationResult validationResult;

    /**
     * Instantiates a new String validation, in which all the sub-validations can be chained together.
     * @param validateMe The String that shall be validated.
     */
    public StringValidation(String validateMe) {
        this.validateMe = validateMe;
        this.validationResult = new ValidationResult();
    }

    /**
     * Checks whether the String is exactly "twin".
     * @param twin The String that shall be checked for equality.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringValidation isExactly(String twin) {
        if ( !this.validateMe.equals(twin)) {
            return addError(String.format("should be exactly %s", twin));
        }

        return this;
    }

    /**
     * Checks whether the String is not null.
     * @return Returns the validation object if there is something in the array, else it adds an error message as well.
     */
    public StringValidation isNotNull() {
        if (validateMe == null) {
            return addError("should not be null");
        }

        return this;
    }

    /**
     * Checks whether the String is in a Set of Strings, represented by a String array.
     * @param set The String array the String shall be in.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringValidation isInSet(String[] set) {
        StringBuilder setRepresentation = new StringBuilder();
        for (int i = 0; i < set.length; i++) {
            setRepresentation.append(String.format("\"%s\"", set[i]));
            setRepresentation.append(i < set.length - 1 ? " or " : "");

            if (set[i].equals(validateMe)) {
                return this;
            }
        }

        return addError(String.format("should be either %s", setRepresentation.toString()));
    }

    /**
     * Throws all collected errors, if there are any.
     * @param paramName The name of the validation object that shall occur in the error message.
     * @return Returns the current Validation object, so that other validation can be applied.
     * @throws ValidationException Thrown if any errors occurred so far.
     */
    public StringValidation throwIfInvalid(String paramName) throws ValidationException {
        if (this.hasFailed()) {
            throw new ValidationException(paramName, this.getErrors());
        }

        return this;
    }

    /**
     * Checks if the validation has failed so far.
     * @return Returns true if an error occurred so far.
     */
    public boolean hasFailed() {
        return this.validationResult.failed();
    }

    /**
     * Gets the error messages that have been chained together so far.
     * @return The error messages.
     */
    public String getErrors() {
        return this.validationResult.getMessage();
    }

    /**
     * Adds an errorMessage message to the error-message String of the validation object.
     * @param errorMessage The message that shall be added.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    private StringValidation addError(String errorMessage) {
        if (errorMessage == null || errorMessage.equals("")) { // No errors to be added. Empty call.
            return this;
        }

        this.validationResult.addValidationError(errorMessage);
        return this;
    }

    /**
     * Returns the validated result.
     * @return The validated String.
     */
    public String getResult() {
        return this.validateMe;
    }
}