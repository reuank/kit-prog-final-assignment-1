package edu.kit.informatik;

import edu.kit.informatik.exceptions.InvalidGameOptionsException;
import edu.kit.informatik.exceptions.ParserException;
import edu.kit.informatik.game.ConnectSix;
import edu.kit.informatik.game.commands.*;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.game.parser.ConnectSixParser;
import edu.kit.informatik.interfaces.ICommand;
import edu.kit.informatik.userinterface.CLI;
import edu.kit.informatik.userinterface.commands.QuitCommand;

public class Main {
    public static void main(String[] args) {
        CLI userInterface = new CLI(new ConnectSixParser());
        try {
            GameOptions gameOptions = userInterface.getParser().parseGameOptions(args);
            ConnectSix game = new ConnectSix(gameOptions);

            userInterface.registerCommands(new ICommand[]{
                new ColprintCommand(game),
                new PlaceCommand(game),
                new PrintCommand(game),
                new QuitCommand(userInterface),
                new ResetCommand(game),
                new RowprintCommand(game),
                new StateCommand(game)
            });

            userInterface.init(); // treffender benennen!
        } catch (ParserException | InvalidGameOptionsException exception) {
            userInterface.outputError(exception.getMessage());
        }
    }
}
