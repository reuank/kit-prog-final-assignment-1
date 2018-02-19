package edu.kit.informatik.game.parser;

import edu.kit.informatik.exceptions.ParserException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.interfaces.ICommand;

public class ConnectSixParser {
    private CommandParser commandParser = new CommandParser();
    private GameOptionsParser gameOptionsParser = new GameOptionsParser();

    public ICommand parseCommand(String input) throws ParserException {
        return commandParser.parse(input);
    };

    public ICommand parseCommand(String[] inputArray) throws ParserException {
        return commandParser.parse(inputArray);
    };

    public GameOptions parseGameOptions(String input) throws ParserException {
        return gameOptionsParser.parse(input);
    };

    public GameOptions parseGameOptions(String[] inputArray) throws ParserException {
        return gameOptionsParser.parse(inputArray);
    };
}