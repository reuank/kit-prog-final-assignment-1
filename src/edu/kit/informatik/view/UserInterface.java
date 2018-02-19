package edu.kit.informatik.view;

import edu.kit.informatik.Terminal;

public class UserInterface {
    /**
     * Reads string input from the Terminal
     * @return Returns the
     */
    public String readInput() {
        String input = Terminal.readLine();
        return input;
    }

    /**
     * Outputs the passed string using the Terminal class
     * @param output The String that should be printed
     */
    public void output(String output) {
        Terminal.printLine(output);
    }

    public void outputError(String output) {
        Terminal.printError(output);
    }
}