package edu.kit.informatik.game.board;

import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;
import edu.kit.informatik.exceptions.PositionOccupiedException;
import edu.kit.informatik.game.GameOptions;

public class TorusBoard extends GameBoard {
    public TorusBoard(GameOptions gameOptions) {
        this.field = new int[gameOptions.getFieldSize()][gameOptions.getFieldSize()];
    }

    public Position convertPosition(Position point) {
        int newX = Math.floorMod(point.getCol(), field.length);
        int newY = Math.floorMod(point.getRow(), field.length);
        return new Position(newX, newY);
    }

    public boolean isWall(Position position) {
        return false;
    }
}
