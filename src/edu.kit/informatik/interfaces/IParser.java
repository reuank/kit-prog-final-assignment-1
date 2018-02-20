package edu.kit.informatik.interfaces;

import edu.kit.informatik.exceptions.ParserException;

public interface IParser<T> {
    /**
     * Parses an input String to a specific Object.
     * @param input The String that shall be parsed.
     * @return Returns a new  object instance of the class.
     * @throws ParserException Thrown if parsing is impossible.
     */
    T parse(String input) throws ParserException;

    /**
     * Parses an input String array to a specific Object.
     * @param input The String array that shall be parsed.
     * @return Returns a new  object instance of the class.
     * @throws ParserException Thrown if parsing is impossible.
     */
    T parse(String[] input) throws ParserException;
}