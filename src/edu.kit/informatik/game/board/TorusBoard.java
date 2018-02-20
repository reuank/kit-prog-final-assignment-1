package edu.kit.informatik.game.board;

import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.game.GameOptions;

public class TorusBoard extends GameBoard {
    /**
     * Instantiates a new torus game board.
     * @param gameOptions The game options that contain the parameters of the game board.
     */
    public TorusBoard(GameOptions gameOptions) {
        if (gameOptions.getGameMode().equals("torus")) {
            this.field = new int[gameOptions.getFieldSize()][gameOptions.getFieldSize()];
            this.lineFinder = new LineFinder(this);
        } else {
            throw new IllegalArgumentException("Program failed. Called the game board wrong constructor.");
        }
    }

    @Override
    public int convertCoordinate(int coordinate, boolean gameModeDependent) throws CoordsOutOfBoundsException {
        if (!gameModeDependent) { // If the standard coordinate conversion is wanted
            return convertCoordinate(coordinate);
        }

        return Math.floorMod(coordinate, field.length);
    }

    @Override
    public boolean isOutsideBoard(Position position) {
        return false;
    }
}
