package edu.kit.informatik.validation;

import edu.kit.informatik.validation.types.IntValidation;
import edu.kit.informatik.validation.types.StringArrayValidation;
import edu.kit.informatik.validation.types.StringValidation;

public class SyntaxValidator {
    public static StringValidation validateString(String input) {
        return new StringValidation(input);
    }

    public static StringArrayValidation validateArray(String[] input) {
        return new StringArrayValidation(input);
    }

    public static IntValidation validateInt(String input) {
        return new IntValidation(input);
    }

    public static IntValidation validateInt(int input) {
        return new IntValidation(input);
    }
}
