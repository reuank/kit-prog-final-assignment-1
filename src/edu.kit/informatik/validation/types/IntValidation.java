package edu.kit.informatik.validation.types;

import edu.kit.informatik.constructs.math.IntRange;
import edu.kit.informatik.constructs.program.ValidationResult;
import edu.kit.informatik.exceptions.ValidationException;

public class IntValidation {
    private int validateMe;
    private ValidationResult validationResult;

    public IntValidation(String validateMe) {
        this.validationResult = new ValidationResult();

        try {
            this.validateMe = Integer.parseInt(validateMe);
        } catch (NumberFormatException exception) {
            addError("is not an integer");
        }
    }

    public IntValidation(int validateMe) {
        this.validateMe = validateMe;
        this.validationResult = new ValidationResult();
    }

    public IntValidation isInRange(int lowerBound, int upperBound) {
        if (this.validateMe < lowerBound || this.validateMe > upperBound) {
            return addError("should be in between " + lowerBound + " and " + upperBound);
        }

        return this;
    }

    public IntValidation isExactly(int twin) {
        if (this.validateMe != twin) {
            return addError("should be exactly " + twin);
        }

        return this;
    }

    public IntValidation isInIntRange(IntRange intRange) {
        if (this.validateMe < intRange.getLowerBound() || this.validateMe > intRange.getUpperBound()) {
            return addError("should be in between " + intRange.getLowerBound() + " and " + intRange.getUpperBound());
        }

        return this;
    }

    public IntValidation isGreaterThan(int lowerBound) {
        if (this.validateMe <= lowerBound ) {
            return addError("should be greater than " + lowerBound);
        }

        return this;
    }

    public IntValidation isLessThan(int upperBound) {
        if (this.validateMe >= upperBound ) {
            return addError("should be less than  " + upperBound);
        }

        return this;
    }

    public IntValidation isPositive() {
        if (this.validateMe < 0 ) {
            return addError("should be positive");
        }

        return this;
    }

    public IntValidation isNegative() {
        if (this.validateMe > 0 ) {
            return addError("should be negative");
        }

        return this;
    }

    public IntValidation isEven() {
        if (this.validateMe % 2 != 0 ) {
            return addError("should be even");
        }

        return this;
    }

    public IntValidation isOdd() {
        if ( this.validateMe % 2 == 0 ) {
            return addError("should be odd");
        }

        return this;
    }

    public IntValidation isMultipleOf(int value) {
        if(this.validateMe % value != 0) {
            return addError("should be a multiple of " + value);
        }

        return this;
    }

    public IntValidation throwIfInvalid(String paramName) throws ValidationException {
        if (this.validationResult.failed()) {
            throw new ValidationException(paramName + " " + validationResult.getMessage() + ".");
        }

        return this;
    }

    public String getErrors(String paramName) {
        if (this.validationResult.failed()) {
            return paramName + " " + this.validationResult.getMessage();
        }

        return null;
    }

    public boolean hasFailed() {
        return this.validationResult.failed();
    }

    private IntValidation addError(String error) {
        validationResult.addValidationError(error);
        return this;
    }

    public int getResult() {
        return this.validateMe;
    }
}