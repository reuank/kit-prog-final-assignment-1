package edu.kit.informatik.game.parser;

import edu.kit.informatik.exceptions.ParserException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.interfaces.IParser;
import edu.kit.informatik.validation.SyntaxValidator;

public class GameOptionsParser implements IParser<GameOptions> {
    @Override
    public GameOptions parse(String input) throws ParserException {
        String[] inputArray = input.split("\\s", -1);
        return parse(inputArray);
    }

    @Override
    public GameOptions parse(String[] inputArray) throws ParserException {
        try {
            // Rudimentary Validation
            SyntaxValidator.validateArray(inputArray)
                    .isNotEmpty()
                    .throwIfInvalid("the passed game options array")
                    .isOfLength(3)
                    .throwIfInvalid("the passed game options array");

            String gameMode = inputArray[0];

            int fieldSize = SyntaxValidator.validateInt(inputArray[1])
                    .throwIfInvalid("field size")
                    .getResult();

            int playerCount = SyntaxValidator.validateInt(inputArray[2])
                    .throwIfInvalid("player count")
                    .getResult();

            return new GameOptions(gameMode, fieldSize, playerCount);
        } catch (ValidationException validationException) {
            throw new ParserException("could not parse game options, because " + validationException.getMessage());
        }
    }
}