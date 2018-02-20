package edu.kit.informatik.game.validation.types;

import edu.kit.informatik.constructs.program.Command;
import edu.kit.informatik.constructs.program.CommandSignature;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.validation.SyntaxValidator;

public class CommandValidation {
    private ICommand validateMe;

    public CommandValidation(ICommand command, CommandSignature commandSignature) throws ValidationException {
        this.validateMe = command;
        validate(validateMe, commandSignature);
    }

    private void validate(ICommand command, CommandSignature commandSignature) throws ValidationException {
        SyntaxValidator.validateString(command.getSlug())
                .isNotNull()
                .isExactly(commandSignature.getSlug())
                .throwIfInvalid("command slug");

        SyntaxValidator.validateArray(command.getArgs())
                .isOfLength(commandSignature.getArgCount())
                .throwIfInvalid("command arguments list")
                .checkTypes(commandSignature.getArgTypes())
                .throwIfInvalid("command parameter");
    }

    public ICommand getResult() {
        return this.validateMe;
    }
}