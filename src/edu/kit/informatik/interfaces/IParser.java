package edu.kit.informatik.interfaces;

import edu.kit.informatik.exceptions.ParserException;
import edu.kit.informatik.exceptions.ValidationException;

import java.text.ParseException;

public interface IParser<T> {
    T parse(String input) throws ParserException, ValidationException;
    T parse(String[] input) throws ParserException, ValidationException;
}