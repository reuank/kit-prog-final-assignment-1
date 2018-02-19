package edu.kit.informatik.userinterface.commands;

import edu.kit.informatik.constructs.program.Command;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.userinterface.CLI;

public abstract class UserInterfaceCommand extends Command<CLI> {
    public abstract boolean tryToExecute(ICommand passedCommand, StringBuilder outputStream) throws InvalidCallOfCommandException;
}
