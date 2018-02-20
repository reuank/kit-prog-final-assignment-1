package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.constructs.program.Command;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IExecutableCommand;
import edu.kit.informatik.userinterface.CLI;

public abstract class UserInterfaceCommand extends Command {
    public UserInterfaceCommand(String slug, String[] args) {
        super(slug, args);
    }

    public abstract boolean tryToExecute(ICommand cmd, StringBuilder outputStream) throws InvalidCallOfCommandException;
}
