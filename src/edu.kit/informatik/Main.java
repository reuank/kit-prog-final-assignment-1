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
 * - Input processing workflow: Input -> Parsing -> Validation -> Execution (if Executable command) -> Output
 * - The Executable commands are used for semantic validation and then passing the data to the game instance
 * - All the game logic itself is implemented in the ConnectSix class.
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
