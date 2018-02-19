package edu.kit.informatik.game.commands;

import edu.kit.informatik.constructs.program.Command;
import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.game.ConnectSix;

public abstract class ConnectSixCommand extends Command<ConnectSix> implements ICommand {
    private ConnectSix context;

    public ConnectSixCommand() {

    }

    public abstract boolean tryToExecute(ICommand command, StringBuilder output) throws InvalidCallOfCommandException;
}