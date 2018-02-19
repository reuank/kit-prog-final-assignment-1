package edu.kit.informatik.constructs.program;

import edu.kit.informatik.exceptions.InvalidCallOfCommandException;
import edu.kit.informatik.interfaces.ICommand;

public class Command<C> implements ICommand {
    private C context;
    private String slug;
    private String[] args;

    public Command() {

    }

    public Command(String slug, String[] args) {
        this.slug = slug;
        this.args = args;
    }

    public String getSlug() {
        return this.slug;
    }

    public String[] getArgs() {
        return this.args;
    }

    public String getArg(int index) {
        return (this.args != null && this.args.length > index) ? this.args[index] : null;
    }

    public boolean tryToExecute(ICommand command, StringBuilder outputStream) throws InvalidCallOfCommandException {
        return false; // To implement
    }
}