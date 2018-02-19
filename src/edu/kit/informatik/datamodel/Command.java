package edu.kit.informatik.datamodel;

public class Command {
    private String slug;
    private String[] arguments;
    private String[][] requiredArguments;

    public Command(String slug, String[] arguments) {
        this.slug = slug;
        this.arguments = arguments;
    }

    /**
     * @return Returns the Command Slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * @return Returns the commands arguments, so null if empty
     */
    public String[] getArguments() {
        return arguments;
    }

    public boolean hasArguments() {
        return this.getArguments() != null;
    }
}