package edu.kit.informatik.game.parser.types;

import edu.kit.informatik.constructs.program.Command;
import edu.kit.informatik.exceptions.ParserException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IParser;
import edu.kit.informatik.validation.SyntaxValidator;

/**
 * Used for parsing a String to a command object.
 */
public class CommandParser implements IParser<ICommand> {
    @Override
    public ICommand parse(String input) throws ParserException {
        String[] inputArray = input.split("\\s", -1);
        return parse(inputArray);
    }

    @Override
    public ICommand parse(String[] inputArray) throws ParserException {
        try {
            SyntaxValidator.validateArray(inputArray)
                    .isNotEmpty()
                    .isOfLengthBetween(1, 2)
                    .throwIfInvalid("the passed command data array");

            String slug = inputArray[0];
            String[] arguments;

            if (inputArray.length > 1) {
                arguments = inputArray[1].split(";", -1);
            } else {
                arguments = null;
            }

            return new Command(slug, arguments);
        } catch (ValidationException validationException) {
            throw new ParserException("command", validationException.getMessage());
        }
    }
}