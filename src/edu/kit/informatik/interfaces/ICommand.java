package edu.kit.informatik.interfaces;

import edu.kit.informatik.exceptions.InvalidCallOfCommandException;

public interface ICommand {
    String getSlug();
    String[] getArgs();
    String getArg(int index);
    boolean tryToExecute(ICommand command, StringBuilder output) throws InvalidCallOfCommandException;
}
