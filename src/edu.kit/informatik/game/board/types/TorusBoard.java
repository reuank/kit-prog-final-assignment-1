package edu.kit.informatik.game.board.types;

import edu.kit.informatik.constructs.specific.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.game.board.GameBoard;
import edu.kit.informatik.game.board.LineFinder;

/**
 * The board used when playing in game mode "torus".
 */
public class TorusBoard extends GameBoard {
    /**
     * Instantiates a new torus game board.
     * @param gameOptions The game options that contain the parameters of the game board.
     */
     public TorusBoard(GameOptions gameOptions) {
        if (gameOptions.getGameMode().equals("torus")) {
            this.setField(new int[gameOptions.getFieldSize()][gameOptions.getFieldSize()]);
            this.setLineFinder(new LineFinder(this));
        } else {
            throw new IllegalArgumentException("Program failed. Called the wrong game board constructor.");
        }
    }

    @Override
    public int convertCoordinate(int coordinate, boolean gameModeDependent) throws CoordsOutOfBoundsException {
        if (!gameModeDependent) { // If the standard coordinate conversion is wanted
            return convertCoordinate(coordinate);
        }

        return Math.floorMod(coordinate, this.getFieldSize());
    }

    @Override
    public boolean isOutsideBoard(Position position) {
        return false;
    }
}
