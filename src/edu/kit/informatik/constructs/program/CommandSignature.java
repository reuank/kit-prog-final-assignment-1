package edu.kit.informatik.constructs.program;

public class CommandSignature {
    private String commandSignature;
    private String slug;
    private String[][] args;

    public CommandSignature(String commandSignature) {
        this.commandSignature = commandSignature;
        String[] inputArray = commandSignature.split("\\s");

        this.slug = inputArray[0];
        String[] arguments;
        if (inputArray.length > 1) {
            arguments = inputArray[1].split(";");
            this.args = new String[arguments.length][2];
            for (int i = 0; i < arguments.length; i++) {
                args[i] = arguments[i].split(":");
            }
        } else {
            arguments = null;
            this.args = null;
        }
    }

    public boolean hasArguments() {
        return getArgCount() > 0;
    }

    public String[][] getArgs() {
        return this.args;
    }

    public int getArgCount() {
        return (this.args == null) ? 0 : this.args.length;
    }

    public String getArgType(int index) {
        return hasArguments() ? this.args[index][1] : null;
    }

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

    public String[] getArgNames(int index) {
        if (hasArguments()) {
            String[] argNameList = new String[this.args.length];
            for (int i = 0; i < argNameList.length; i++) {
                argNameList[i] = getArgName(i);
            }
            return argNameList;
        }
        return null;
    }

    public String getCommandSignature() {
        return commandSignature;
    }

    public String getSlug() {
        return this.slug;
    }
}
