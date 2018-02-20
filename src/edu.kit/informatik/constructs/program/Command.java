package edu.kit.informatik.constructs.program;

import edu.kit.informatik.interfaces.ICommand;

/**
 * Only a Class that holds data. These Commands are not yet executable, but at least have the structure of a Command.
 * These Objects will be passed to the corresponding Class that implements IExecutableCommand, where the set of
 * instructions is held.
 */
public class Command implements ICommand {
    private String slug;
    private String[] args;

    /**
     * Instantiates a new Command Object.
     * @param slug The slug the command has.
     * @param args The corresponding command arguments.
     */
    public Command(String slug, String[] args) {
        this.slug = slug;
        this.args = args;
    }

    @Override
    public String getSlug() {
        return this.slug;
    }

    @Override
    public String[] getArgs() {
        return this.args;
    }

    @Override
    public String getArg(int index) {
        return (this.args != null && this.args.length > index) ? this.args[index] : null;
    }
}