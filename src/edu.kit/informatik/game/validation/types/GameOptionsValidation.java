package edu.kit.informatik.game.validation.types;

import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.validation.SyntaxValidator;

/**
 * Used to execute the validation of the game options. Performs the necessary semantic checks on the game options data.
 */
public class GameOptionsValidation {
    private GameOptions validateMe;

    /**
     * Instanciates a new game options validation object, in which error messages can be chained together.
     * @param gameOptions The already parsed and syntactically correct game options, that shall now be validated for
     *                    semantic correctness.
     * @throws ValidationException Thrown, if the passed game options are semantically incorrect.
     */
    public GameOptionsValidation(GameOptions gameOptions) throws ValidationException {
        this.validateMe = gameOptions;
        validate(validateMe);
    }

    /**
     * Executes the game options validation chain that consists of multiple semantic checks.
     * @param gameOptions The passed game options data that shall be validated.
     * @throws ValidationException Thrown, if the passed game options are semantically incorrect.
     */
    private void validate(GameOptions gameOptions) throws ValidationException {
        SyntaxValidator.validateString(gameOptions.getGameMode())
                .isInSet(GameOptions.ALLOWED_GAME_MODES)
                .throwIfInvalid("Game mode");

        SyntaxValidator.validateInt(gameOptions.getFieldSize())
                .isInIntRange(GameOptions.ALLOWED_FIELD_SIZE)
                .isEven()
                .throwIfInvalid("Field size");

        SyntaxValidator.validateInt(gameOptions.getPlayerCount())
                .isInIntRange(GameOptions.ALLOWED_PLAYER_COUNT)
                .throwIfInvalid("Player count");
    }

    /**
     * Returns the validated game options.
     * @return The validated game options.
     */
    public GameOptions getResult() {
        return this.validateMe;
    }
}