package edu.kit.informatik.game.parser;

import edu.kit.informatik.exceptions.ParserException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.interfaces.IExecutableCommand;

public class ConnectSixParser {
    /**
     * Holds instances of the two parsers that belong to the game and passes the data to them.
     */
    private CommandParser commandParser = new CommandParser();
    private GameOptionsParser gameOptionsParser = new GameOptionsParser();

    /**
     * Parses an input String to a new Command object.
     * @param input The String that shall be parsed to a command object.
     * @return Returns a new Command object if the parsing was successful.
     * @throws ParserException Thrown if parsing failed due to an invalid structure of the provided input data.
     */
    public ICommand parseCommand(String input) throws ParserException {
        return commandParser.parse(input);
    };

    /**
     * Parses an input String array to a new Command object.
     * @param inputArray The String array that shall be parsed, consisting of at least the command slug.
     * @return Returns a new Command object if the parsing was successful.
     * @throws ParserException Thrown if parsing failed due to an invalid structure of the provided input data.
     */
    public ICommand parseCommand(String[] inputArray) throws ParserException {
        return commandParser.parse(inputArray);
    };

    /**
     * Parses an input String to a new game options object.
     * @param input The String that shall be parsed to a game options object.
     * @return Returns a new GameOptions object if parsing was successful.
     * @throws ParserException Thrown if parsing failed due to an invalid structure of the provided input data.
     */
    public GameOptions parseGameOptions(String input) throws ParserException {
        return gameOptionsParser.parse(input);
    };

    /**
     *  Parses an input String array to a new game options object.
     * @param inputArray The String array that shall be parsed to a game options object.
     * @return Returns a new GameOptions object if parsing was successful.
     * @throws ParserException Thrown if parsing failed due to an invalid structure of the provided input data.
     */
    public GameOptions parseGameOptions(String[] inputArray) throws ParserException {
        return gameOptionsParser.parse(inputArray);
    };
}