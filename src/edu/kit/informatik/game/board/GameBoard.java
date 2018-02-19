package edu.kit.informatik.game.board;

import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidGameOptionsException;
import edu.kit.informatik.exceptions.PositionOccupiedException;
import edu.kit.informatik.game.GameOptions;
import javafx.geometry.Pos;

public abstract class GameBoard {
    int[][] field;
    LineFinder lineFinder;

    public static GameBoard getNewBoard(GameOptions gameOptions) throws InvalidGameOptionsException {
        if (gameOptions == null) {
            throw new InvalidGameOptionsException("please enter game options.");
        }

        if (gameOptions.getGameMode().equals("standard")) {
            return new StandardBoard(gameOptions);
        }

        if (gameOptions.getGameMode().equals(("torus"))) {
            return new TorusBoard(gameOptions);
        }

        throw new InvalidGameOptionsException("unable to create game board.");
    }

    /**
     *
     * @param position
     * @return
     * @throws CoordinatesOutOfBoundsException
     */
    public abstract Position convertPosition(Position position) throws CoordinatesOutOfBoundsException;

    /**
     *
     * @param col
     * @return
     * @throws CoordinatesOutOfBoundsException
     */
    public int convertCol(int col) throws CoordinatesOutOfBoundsException {
        return convertPosition(new Position(col, 0)).getCol();
    }

    /**
     *
     * @param row
     * @return
     * @throws CoordinatesOutOfBoundsException
     */
    public int convertRow(int row) throws CoordinatesOutOfBoundsException {
        return convertPosition(new Position(0, row)).getRow();
    }

    public Position tryPlace(int player, Position position) throws PositionOccupiedException, CoordinatesOutOfBoundsException {
        Position convertedPosition = convertPosition(position);
        int state = getState(convertedPosition);
        if (state == 0) {
            place(player, convertedPosition);
            return convertedPosition;
        }

        throw new PositionOccupiedException("the field " + convertedPosition.getRow() + ";" + convertedPosition.getCol()
                + " is already occupied by Player " + state + ".");
    }

    public void place(int value, Position position) {
        this.field[position.getRow()][position.getCol()] = value;
    }

    public boolean findRayLines(Position beginAt, int maxLength) throws CoordinatesOutOfBoundsException {
        return this.lineFinder.findRayLines(beginAt, maxLength);
    }

    public int[] getRow(int rowId) throws CoordinatesOutOfBoundsException {
        return this.field[convertRow(rowId)];
    }

    public int[] getCol(int colId) throws CoordinatesOutOfBoundsException {
        int newColId = convertCol(colId);
        int[] col = new int[getFieldSize()];
        for (int i = 0; i < getFieldSize(); i++) {
            col[i] = this.field[i][colId];
        }
        return col;
    }

    public void reset() {
        this.field = new int[this.field.length][this.field.length];
    }

    public int getFieldSize() {
        return this.field.length;
    }

    public int getState(Position position) throws CoordinatesOutOfBoundsException {
        Position convertedPoint = convertPosition(position);
        return this.field[convertedPoint.getRow()][convertedPoint.getCol()];
    }

    public void undo(Position point) {
        this.field[point.getCol()][point.getRow()] = 0;
    }

    public abstract boolean isWall(Position position);
}
