package edu.kit.informatik.game.validation;

import edu.kit.informatik.constructs.program.Command;
import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.game.validation.types.CommandValidation;
import edu.kit.informatik.game.validation.types.GameOptionsValidation;
import edu.kit.informatik.interfaces.ICommand;

public class ConnectSixValidator {
    // Game Options Validation
    public static GameOptionsValidation validateGameOptions(GameOptions gameOptions) throws ValidationException {
        return new GameOptionsValidation(gameOptions);
    }

    // Command Validation
    public static CommandValidation validateCommand(ICommand cmd, CommandSignature sign) throws ValidationException {
        return new CommandValidation(cmd, sign);
    }
}