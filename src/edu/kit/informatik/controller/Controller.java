package edu.kit.informatik.controller;

import edu.kit.informatik.datamodel.Command;
import edu.kit.informatik.datamodel.Game;
import edu.kit.informatik.datamodel.GameOptions;
import edu.kit.informatik.view.UserInterface;

public class Controller {
    private Parser parser;
    private Validator validator;
    private OutputBuilder outputBuilder;
    private Game game; // Model
    private UserInterface ui; // View
    private int currentPlayer;

    /**
     * Instantiates Controller that handles abstract data-model and the user interface
     */
    public Controller(String[] passedGameOptions) { // gameOptions übergeben?
        validator = new Validator();
        ui = new UserInterface();
        outputBuilder = new OutputBuilder();
        parser = new Parser();
        try{
            GameOptions gameOptions = parser.parseGameOptions(passedGameOptions);
            validator.validateGameOptions(gameOptions);
            game = new Game(gameOptions);
        } catch (UnsupportedOperationException | IllegalArgumentException exception) {
            this.ui.output(exception.getMessage());
        }
    }

    public void run(){
        String passedCommand = ui.readInput();
        try {
            Command currentCommand = parser.parseCommand(passedCommand);
            validator.validateCommand(currentCommand);
            currentPlayer = 0;

            while (!currentCommand.getSlug().equals("quit")) {
                executeCommand(currentCommand);
                passedCommand = ui.readInput();
                currentCommand = parser.parseCommand(passedCommand);
            }
        } catch (UnsupportedOperationException | IllegalArgumentException exception) {
            this.ui.outputError(exception.getMessage());
        }
    }

    public boolean isInitialised() {
        return parser != null && outputBuilder != null && game != null;
    }

    private void executeCommand(Command command) {
        String output;

        try{
            validator.validateCommand(command);
        } catch (UnsupportedOperationException | IllegalArgumentException exception) {
            this.ui.outputError(exception.getMessage());
            return;
        }

        switch (command.getSlug()) {
            case "print":
                output = outputBuilder.buildField(game);
                this.ui.output(output);
                break;
            case "place":
                int row1 = Integer.parseInt(command.getArguments()[0]);
                int col1 = Integer.parseInt(command.getArguments()[1]);
                int row2 = Integer.parseInt(command.getArguments()[2]);
                int col2 = Integer.parseInt(command.getArguments()[3]);

                game.getField().setValue(row1, col1, currentPlayer + 1);
                game.getField().setValue(row2, col2, currentPlayer + 1);
                // Was ist, wenn das zweite Feld schon besetzt ist? => Exception */

                currentPlayer = (currentPlayer + 1) % game.getGameOptions().getPlayerCount();
                this.ui.output("OK");
                break;
            case "rowprint":
                int row = Integer.parseInt(command.getArguments()[0]);
                output = outputBuilder.buildRow(game, row);
                this.ui.output(output);
                break;
            case "reset":
                this.game.reset();
                currentPlayer = 0;
                this.ui.output("OK");
                break;
            default:
                this.ui.output("Command not valid. Please try again or quit the game by entering quit.");
                break;
        }
    }
}
