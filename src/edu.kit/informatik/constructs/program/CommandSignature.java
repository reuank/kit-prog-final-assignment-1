package edu.kit.informatik.constructs.program;

/**
 * This class is the blueprint for a command signature. A signature specifies how a valid command call looks like.
 * In the command signature, the command slug and the command arguments are encoded.
 */
public class CommandSignature {
    private String commandSignature;
    private String slug;
    private String[][] args;

    /**
     * Instantiates a new command signature.
     * @param commandSignature The signature String of a command.
     *                         The signature itself has the following structure: [command slug] [command arguments]
     *                         While [command arguments] have the following structure: paramName:type;paramName:type...
     *                         Each parameter has a name and a type. Currently the type can only be "int",
     *                         but in the future there can be added float checks or special String types e.g.
     */
    public CommandSignature(String commandSignature) {
        this.commandSignature = commandSignature;
        String[] inputArray = commandSignature.split("\\s");

        this.slug = inputArray[0];
        String[] arguments;

        if (inputArray.length > 1) { // Check if the command signature actually has any arguments
            arguments = inputArray[1].split(";");
            this.args = new String[arguments.length][2];
            for (int i = 0; i < arguments.length; i++) {
                args[i] = arguments[i].split(":");
            }
        } else {
            this.args = null;
        }
    }

    /**
     * Reutns the actual signature.
     * @return The actual signature.
     */
    public String getCommandSignature() {
        return commandSignature;
    }

    /**
     * Gets the slug of the command signature.
     * @return Returns the slug.
     */
    public String getSlug() {
        return this.slug;
    }

    /**
     * Gets all the arguments in the command signature, split into argument name and datatype.
     * @return Returns the argument structure as a two dimensional array.
     */
    public String[][] getArgs() {
        return this.args;
    }

    /**
     * Checks if there are any arguments in the signature.
     * @return Returns true if there are arguments.
     */
    public boolean hasArguments() {
        return getArgCount() > 0;
    }

    /**
     * Gets the number of arguments that belong to this signature.
     * @return Returns the number of arguments that belong to this command signature.
     */
    public int getArgCount() {
        return (this.args == null) ? 0 : this.args.length;
    }

    /**
     * Gets the datatype of the argument at a specific position in the argument list.
     * @param index the index of the argument of which the datatype shall be returned.
     * @return The datatype of the argument.
     */
    public String getArgType(int index) {
        return hasArguments() ? this.args[index][1] : null;
    }

    /**
     * Gets all the datatypes of the arguments as an array.
     * @return Returns an array of all the argument types of the command signature.
     */
    public String[] getArgTypes() {
        if (hasArguments()) {
            String[] argTypesList = new String[this.args.length];

            for (int i = 0; i < argTypesList.length; i++) {
                argTypesList[i] = getArgType(i);
            }

            return argTypesList;
        }

        return null;
    }

    /**
     * Used to get the name of the actual parameter.
     * @param index the index of the parameter name you want to get.
     * @return Returns the name of the parameter at the given index.
     */
    public String getArgName(int index) {
        if (hasArguments()) {
            String paramName = this.args[index][0];

            if (paramName == null) {
                return "argument on position " + index;
            }

            return paramName;
        }

        return null;
    }

    /**
     * Gets the human readable argument names.
     * @return Returns an array of all the argument names corresponding to this command signature.
     */
    public String[] getArgNames() {
        if (hasArguments()) {
            String[] argNameList = new String[this.args.length];

            for (int i = 0; i < argNameList.length; i++) {
                argNameList[i] = getArgName(i);
            }

            return argNameList;
        }

        return null;
    }
}