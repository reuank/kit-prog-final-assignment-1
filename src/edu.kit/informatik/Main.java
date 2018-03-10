package edu.kit.informatik;

import edu.kit.informatik.exceptions.InvalidGameOptionsException;
import edu.kit.informatik.exceptions.ParserException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.game.commands.*;
import edu.kit.informatik.game.parser.ConnectSixParser;
import edu.kit.informatik.interfaces.IExecutableCommand;
import edu.kit.informatik.userinterface.CLI;
import edu.kit.informatik.userinterface.commands.QuitCommand;

/**
 * @author Leon Knauer
 *
 * General comments:
 * -    I intentionally designed this program in a framework-like way. By doing this, the game can easily be extended:
 *          -   If another command should be added, simply add a new command class, extend IExecutable and
 *              register it in main.
 *          -   If the number of paramters for commands changes, simply adjust the command signature accordingly.
 *          -   If in the future also parameters of another type are allowed, just expand the type validation in the
 *              "String ArrayValidation" class and adjust the command signature accordingly.
 *          -   If any rules of th game change, simply adjust the semantic command validation, the game option constants
 *              or tweak the game logic.
 *          -   If in the future there is the need for implementing translation, this can easily be done:
 *              All messages that are build using String.format, which allows calling a final constant as
 *              first parameter. This means that once all necessary constants are defined, implementing translation
 *              is very easy.
 *          -   Some of the defined functions are not used within this particular project.
 *              This is due to the goal of having as much modularity and therefore expandability as possible.
 *                  E.g.: The IntValidation class also provides the "isExactly()" check, which would be useful if the
 *                  rules of the game only allows one number of players one day.
*           -   Some classes are meant as wrapper classes for simplifying the programming workflow.
 *                  E.g.: By typing "ConnectSixSerializer.", all the available Serializers are listed by Intellisense.
 * -    Input processing workflow: Input -> Parsing -> Validation -> Execution (if Executable command) -> Output
 * -    The Executable commands are used for semantic validation and then passing the data to the game instance
 */

public class Main {

    /**
     * Creates a new user interface and a new game, registers all valid commands and runs the the user interface.
     * @param args The passed program args.
     */
    public static void main(String[] args) {
        CLI userInterface = new CLI(new ConnectSixParser());

        try {
            GameOptions gameOptions = userInterface.getParser().parseGameOptions(args);
            ConnectSix game = new ConnectSix(gameOptions);

            userInterface.registerCommands(new IExecutableCommand[]{
                new ColprintCommand(game),
                new PlaceCommand(game),
                new PrintCommand(game),
                new QuitCommand(userInterface),
                new ResetCommand(game),
                new RowprintCommand(game),
                new StateCommand(game)
            });

            userInterface.run();
        } catch (ParserException | InvalidGameOptionsException exception) {
            userInterface.outputError(exception.getMessage());
        }
    }
}
