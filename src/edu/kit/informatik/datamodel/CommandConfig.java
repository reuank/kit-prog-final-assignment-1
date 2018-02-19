package edu.kit.informatik.datamodel;

public class CommandConfig {
    private String[][] commandConfigArray = {
        // command slug, signature
        // Remember adapting the argumentsValid method when checking for other datatypes then int
            {"place",       "row1:int;col1:int;row2:int;col2:int"},
            {"rowprint",    "row:int"},
            {"colprint",    "col:int"},
            {"state",       "x:int;y:int"},
            {"print",       ""},
            {"reset",       ""},
            {"quit",        ""}
        };


    public int getIdBySlug(String slug) {
        for (int i = 0; i < commandConfigArray.length; i++) {
            if (getSlugById(i).equals(slug)) {
                return i;
            }
        }

        return -1;
    }

    // func: containsCommand
    // func: getNumArgs etc.

    public String getSlugById(int id) {
        return commandConfigArray[id][0];
    }

    public String getSignatureById(int id) {
        return commandConfigArray[id][1];
    }

    public boolean containsCommand(Command command) {
        return getIdBySlug(command.getSlug()) != -1;
    }

    /**
     *
     * @param command
     * @return
     */
    public String[][] getRequiredArgumentStructure(Command command) {
        int commandId = getIdBySlug(command.getSlug());
        String commandSignature = getSignatureById(commandId).trim();

        if (!commandSignature.equals("")) {
            // Handle commands from Main
            String[] requiredArguments = commandSignature.split("\\s");
            if (requiredArguments.length <= 1) {
                // Handle in-game commands
                requiredArguments = commandSignature.split(";");
            }

            // Split name and type of the argument
            String[][] requiredArgumentStructure = new String[requiredArguments.length][2];
            for (int i = 0; i < requiredArgumentStructure.length; i++) {
                requiredArgumentStructure[i] = requiredArguments[i].split(":");
            }

            return requiredArgumentStructure;
        }

        return null;
    }
}

