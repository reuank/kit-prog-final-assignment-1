package edu.kit.informatik.game.validation;

import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.game.validation.types.CommandValidation;
import edu.kit.informatik.game.validation.types.GameOptionsValidation;
import edu.kit.informatik.interfaces.ICommand;

/**
 * The class that manages all the available type validations.
 * This way all the available validations can be accessed via Intellisense by typing "ConnectSixValidator."
 */
public class ConnectSixValidator {
    /**
     * Validates a parsed GameOptions object for semantic correctness.
     * @param gameOptions The game options that shall be validated.
     * @return Returns a new validation-object, which could be used for chaining further validations.
     * @throws ValidationException Thrown if the passed game options are incorrect.
     */
    public static GameOptionsValidation validateGameOptions(GameOptions gameOptions) throws ValidationException {
        return new GameOptionsValidation(gameOptions);
    }

    /**
     * Validates a parsed Command object for semantic correctness.
     * @param cmd The command that shall be validated.
     * @param sign The signature that the command shall be checked against.
     * @return Returns a new validation-object, which could be used for chaining further validations.
     * @throws ValidationException Thrown if the passed command data is incorrect.
     */
    public static CommandValidation validateCommand(ICommand cmd, CommandSignature sign) throws ValidationException {
        return new CommandValidation(cmd, sign);
    }
}