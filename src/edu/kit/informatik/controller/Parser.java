package edu.kit.informatik.controller;

import edu.kit.informatik.datamodel.Command;
import edu.kit.informatik.datamodel.GameOptions;

import java.io.IOException;
import java.io.InvalidClassException;

public class Parser {
    /**
     * Splits the input string into the slug and the arguments
     * @param input The input string: (slug) (arg1);(arg2);...
     * @return Returns the generated command object
     */
    public Command parseCommand(String input) {
        input = input.trim().replaceAll(" +", " ");
        String[] inputArray = input.split("\\s");

        if (inputArray.length > 2){
            return null;
        }

        String slug = inputArray[0];
        String[] arguments;
        if (inputArray.length > 1) {
            arguments = inputArray[1].split(";");
        } else {
            arguments = null;
        }

        return new Command(slug, arguments);
    }


    public GameOptions parseGameOptions(String[] passedGameOptions) {
        String gameMode = passedGameOptions[0];
        int fieldSize = Integer.parseInt(passedGameOptions[1]);
        int playerCount = Integer.parseInt(passedGameOptions[2]);
        return new GameOptions(gameMode, fieldSize, playerCount);
    }

    private String[] removeSpaces(String[] passedArray) {
        String passedGameOptionsString = passedArray.toString();
        return removeSpaces(passedGameOptionsString).split("\\s");
    }

    private String removeSpaces(String passedString) {
        return passedString.trim().replaceAll(" +", "");
    }
}