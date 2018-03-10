package edu.kit.informatik.validation.types;

import edu.kit.informatik.constructs.program.ValidationResult;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.validation.SyntaxValidator;

/**
 * This class holds all applicable String-array validations.
 */
public class StringArrayValidation {
    private String[] validateMe;
    private ValidationResult validationResult;

    /**
     * Instanciates a new String-array validation, in which all the sub-validations can be chained together.
     * @param validateMe The String-array that shall be validated.
     */
    public StringArrayValidation(String[] validateMe) {
        this.validateMe = validateMe;
        this.validationResult = new ValidationResult();
    }

    /**
     * Checks whether the String-array is not null.
     * @return Returns the validation object if there is something in the array, else it adds an error message as well.
     */
    public StringArrayValidation isNotNull() {
        if (this.validateMe == null) {
            return addError("should not be null");
        }

        return this;
    }

    /**
     * Checks whether there are actual values inside the String-array. Adds an error if necessary.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isNotEmpty() {
        for (int i = 0; i < this.validateMe.length; i++) {
            if (!this.validateMe[0].equals("")) { // Found something!
                return this;
            }
        }

        return addError("should not be empty");
    }

    /**
     * Checks whether the String-array has a size bigger or equal to a given value. Adds an error if necessary.
     * @param length The length the String-array shall have at least.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isLongerOrEqualThan(int length) {
        if (this.validateMe == null) {
            return addError("should not be null in order to be checked whether it is longer than " + length);
        }

        if (this.validateMe.length <= length) {
            return addError(String.format("should be longer than %d", length));
        }

        return this;
    }

    /**
     * Checks whether the String-array has a size smaller or equal to a given value. Adds an error if necessary.
     * @param length The length the String-array shall have at most.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isShorterOrEqualThan(int length) {
        if (this.validateMe.length >= length) {
            return addError(String.format("should be shorter than %d", length));
        }

        return this;
    }

    /**
     * Checks whether the String-array has a specific length. Adds an error if necessary.
     * @param length The length the array shall have.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isOfLength(int length) {
        if (this.validateMe == null) {
            if (length == 0) {
                return this;
            } else {
                return addError("has length 0 instead of " + length);
            }
        } else if (this.validateMe.length != length ) {
            return addError(String.format("has length %d instead of %d", this.validateMe.length, length));
        }

        return this;
    }

    /**
     * Checks whether the String-arrays length is in the given bounds. Adds an error if necessary.
     * @param lowerBound The lower (included) bound of the size.
     * @param upperBound The upper (included) bound of the size.
     * @return  Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isOfLengthBetween(int lowerBound, int upperBound) {
        if (this.validateMe == null && lowerBound == 0 && upperBound == 0 ) {
            return this;
        }

        if (this.validateMe.length < lowerBound || this.validateMe.length > upperBound) {
            return addError(String.format("should have length between %d and %d", lowerBound, upperBound));
        }

        return this;
    }

    /**
     * Checks whether the String-array contains a specific String value. Adds an error if necessary.
     * @param needle The String that shall be searched for.
     * @return Returns the current Validation object, so that other validation can be applied.
     */
    public StringArrayValidation isContaining(String needle) {
        if (this.validateMe == null) {
            return addError(String.format("should not be null to be searched for \"%s\"", needle));
        }

        for (String value : this.validateMe) {
            if (value.equals(needle)) { // Desired String found!
                return this;
            }
        }

        return addError("should contain value " + needle);
    }

    /**
     * This method can be used to ensure that the values in a string array can be converted to a specific data-type.
     * Currently the following data-type-checks are supported: int.
     * In the future, there can be added float-checks, regex checks or anything alike.
     * @param argTypes This is the list of datatypes that validateMe should be checked against
     * @return Returns the current Validation object, so that other validation can be applied.
     * @throws ValidationException If the check fails or the input parameters are false, an exception will be thrown.
     */
    public StringArrayValidation checkTypes(String[] argTypes) throws ValidationException {
        // Ensure that there is something to check
        if (this.validateMe == null) {
            // No need to check anything
            if (argTypes == null) {
                return this;
            }

            return addError("validation failed while trying to check the types in the passed arguments list");
        }

        // Number of types to check not equals number of given types
        if (this.validateMe.length != argTypes.length) {
            return addError(String.format("validation failed while trying to check the types in the passed "
                            + "arguments list. The number of types to check (%d) does not match "
                            + "the number of available arguments (%d)",
                    argTypes.length,
                    this.validateMe.length
            ));
        }

        StringBuilder failedIntParams = new StringBuilder();

        for (int i = 0; i < this.validateMe.length; i++) {
            switch (argTypes[i]) {
                case "int":
                    if (SyntaxValidator.validateInt(this.validateMe[i]).hasFailed()) {
                        failedIntParams.append(i + 1);
                        failedIntParams.append("#"); // Add "#" separation, which will be replaces by " and " later.
                    }
                    break;
                default:
                    throw new ValidationException("there is an undefined data-type in the command signature.");
            }
        }

        // Convert the array of failed Integer validations to a human readable list.
        String[] failedIntsArray = failedIntParams.toString().split("#", 0);
        String failedIntsString = String.join(" and ", failedIntsArray);
        addError(!failedIntsString.equals("") ? String.format("number %s is not an integer", failedIntsString) : "");

        return this;
    }

    /**
     * Throws all collected errors, if there are any.
     * @param paramName The name of the validation object that shall occur in the error message.
     * @return Returns the current Validation object, so that other validation can be applied.
     * @throws ValidationException Thrown if any errors occurred so far.
     */
    public StringArrayValidation throwIfInvalid(String paramName) throws ValidationException {
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
    private StringArrayValidation addError(String errorMessage) {
        if (errorMessage == null || errorMessage.equals("")) { // No errors to be added. Empty call.
            return this;
        }

        this.validationResult.addValidationError(errorMessage);
        return this;
    }

    /**
     * Returns the validated result.
     * @return The validated String-array.
     */
    public String[] getResult() {
        return this.validateMe;
    }
}
