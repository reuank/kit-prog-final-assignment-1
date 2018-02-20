package edu.kit.informatik.game.validation.types;

import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.validation.SyntaxValidator;

public class GameOptionsValidation {
    private GameOptions validateMe;

    public GameOptionsValidation(GameOptions gameOptions) throws ValidationException {
        this.validateMe = gameOptions;
        validate(validateMe);
    }

    private void validate(GameOptions gameOptions) throws ValidationException {
        SyntaxValidator.validateString(gameOptions.getGameMode())
                .isInSet(GameOptions.allowedGameModes)
                .throwIfInvalid("Game mode");

        SyntaxValidator.validateInt(gameOptions.getFieldSize())
                .isInIntRange(GameOptions.allowedFieldSize)
                .isEven()
                .throwIfInvalid("Field size");

        SyntaxValidator.validateInt(gameOptions.getPlayerCount())
                .isInIntRange(GameOptions.allowedPlayerCount)
                .throwIfInvalid("Player count");
    }

    public GameOptions getResult() {
        return this.validateMe;
    }
}