package edu.kit.informatik.controller;

import edu.kit.informatik.datamodel.Command;
import edu.kit.informatik.datamodel.CommandConfig;
import edu.kit.informatik.datamodel.GameOptions;
import edu.kit.informatik.datamodel.GameOptionsConfig;

public class Validator {
    private CommandConfig commandConfig = new CommandConfig();
    private GameOptionsConfig gameOptionsConfig = new GameOptionsConfig();

    public boolean validateCommand(Command passedCommand) {
        return isValidCommand(passedCommand);
    }

    private boolean argumentsValid(String[] actualArguments, String[][] requiredArgumentStructure) {
        // Loop through arguments
        for (int i = 0; i < actualArguments.length; i++) {
            String argument = actualArguments[i];
            String requiredDatatype = requiredArgumentStructure[i][1];
            switch (requiredDatatype){
                // Extendable to other parameter types in the future
                case "int":
                    try {
                        Integer.parseInt(argument);
                    } catch (NumberFormatException e){
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        }

        return true;
    }

    private boolean isValidCommand(Command passedCommand) {
        // Check if command has been passed
        if (passedCommand == null)
            throw new IllegalArgumentException("parsing the last command failed. Please try again.");

        // Check if passedCommand exists
        if (!commandConfig.containsCommand(passedCommand))
            throw new UnsupportedOperationException("Entered command \"" + passedCommand.getSlug() + "\" not defined. Try again.");

        // Check if number of arguments is correct
        int commandId = commandConfig.getIdBySlug(passedCommand.getSlug());
        String[][] requiredArgumentStructure = commandConfig.getRequiredArgumentStructure(passedCommand);
        String requiredSignature = commandConfig.getSignatureById(commandId);
        int requiredNumOfArgs = requiredArgumentStructure != null ? requiredArgumentStructure.length : 0;

        // Check whether the entered passedCommand has any arguments and whether this is valid or not
        if (!passedCommand.hasArguments())
            if (requiredNumOfArgs > 0)
                throw new IllegalArgumentException("No arguments entered. Required: " + requiredNumOfArgs);
            else
                return true;

        // Check whether the number of passedCommand arguments matches the requirements
        if (passedCommand.getArguments().length != requiredNumOfArgs)
            throw new IllegalArgumentException("Invalid number of arguments. Required: " + requiredNumOfArgs);

        // Check if the datatypes of the arguments are correct
        if (!argumentsValid(passedCommand.getArguments(), requiredArgumentStructure))
            throw new IllegalArgumentException("Structure of arguments invalid. Required: \""
                    + requiredSignature + "\"");

        return true;
    }

    private boolean isValidGameOptions(GameOptions passedGameOptions) {
        if (passedGameOptions = null )
            throw new
        // Trim spaces
        passedGameOptions = removeSpaces(passedGameOptions);

        // Check if gamemode exits
        String passedGameMode = passedGameOptions[0];
        if (!gameOptionsConfig.containsGameMode(passedGameMode)) {
            throw new UnsupportedOperationException("Entered gamemode \"" + passedGameMode + "\" not defined. Try again.");
        }

        return true;
    }

    public boolean validateGameOptions(GameOptions passedGameOptions) {
        return isValidGameOptions(passedGameOptions);
    }

}
