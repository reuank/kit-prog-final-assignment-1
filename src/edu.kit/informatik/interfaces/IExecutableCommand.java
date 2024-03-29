package edu.kit.informatik.interfaces;

import edu.kit.informatik.exceptions.InvalidCallOfCommandException;

/**
 * An interface for executable commands.
 */
public interface IExecutableCommand extends ICommand {
    /**
     * Tries to execute the given command using the processes defined in the specific command class.
     * @param command The command that shall be executed.
     * @param output The place where all the output of the command - except errors - goes.
     * @throws InvalidCallOfCommandException Thrown if any errors occur during the execution of the command.
     */
    void tryToExecute(ICommand command, StringBuilder output) throws InvalidCallOfCommandException;
}