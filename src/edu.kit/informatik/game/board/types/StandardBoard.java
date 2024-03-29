package edu.kit.informatik.game.board.types;

import edu.kit.informatik.constructs.specific.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.game.board.GameBoard;
import edu.kit.informatik.game.board.LineFinder;

/**
 * The board used when playing in game mode "standard".
 */
public class StandardBoard extends GameBoard {
    /**
     * Instantiates a new standard game board.
     * @param gameOptions The game options that contain the parameters of the game board.
     */
     public StandardBoard(GameOptions gameOptions) {
        if (gameOptions.getGameMode().equals("standard")) {
            this.setField(new int[gameOptions.getFieldSize()][gameOptions.getFieldSize()]);
            this.setLineFinder(new LineFinder(this));
        } else {
            throw new IllegalArgumentException("Program failed. Called the wrong game board constructor.");
        }
    }

    @Override
    public int convertCoordinate(int coordinate, boolean gameModeDependent) throws CoordsOutOfBoundsException {
        return convertCoordinate(coordinate);
    }

    @Override
    public boolean isOutsideBoard(Position position) {
        try {
            convertPosition(position);
            return false;
        } catch (CoordsOutOfBoundsException exception) {
            return true;
        }
    }
}
