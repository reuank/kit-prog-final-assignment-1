package edu.kit.informatik.game.commands;

import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.interfaces.IExecutableCommand;

public abstract class ConnectSixCommand implements IExecutableCommand {
    private ConnectSix context;

    public ConnectSixCommand() {

    }

    public abstract void tryToExecute(IExecutableCommand command, StringBuilder output) throws InvalidCallOfCommandException;
}