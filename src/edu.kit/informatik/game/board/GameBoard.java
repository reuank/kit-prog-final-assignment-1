package edu.kit.informatik.game.board;

import edu.kit.informatik.constructs.specific.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidGameOptionsException;
import edu.kit.informatik.exceptions.PosOccupiedException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.GameOptions;
import edu.kit.informatik.game.board.types.StandardBoard;
import edu.kit.informatik.game.board.types.TorusBoard;
import edu.kit.informatik.validation.SyntaxValidator;

/**
 * The abstract parent class of the game board.
 * All the actions that have to do with the game board itself are defined here.
 * The specific game boards that inherit from this class override the "convertCoordinate" method, because this is
 * the only difference between the game modes so far.
 */
public abstract class GameBoard {
    /** The actual integer array that holds the values of the game board. */
    private int[][] field;

    /** The line finder that belongs to the game board. */
    private LineFinder lineFinder;


    /* ****** CONVERSIONS ****** */

    /**
     * Converts a given coordinate to a valid position on the board.
     * @param position The position that shall be converted.
     * @return Returns the converted position.
     * @throws CoordsOutOfBoundsException Thrown if the position is outside the game board.
     */
    public Position convertPosition(Position position) throws CoordsOutOfBoundsException {
        return new Position(
            this.convertCoordinate(position.getRow(), true),
            this.convertCoordinate(position.getCol(), true)
        );
    }

    /**
     * The standard coordinate conversion. Only allows for values between 0 and N - 1, whereas N is the game board size.
     * @param coordinate The coordinate value that shall be converted.
     * @return The converted and checked coordinate.
     * @throws CoordsOutOfBoundsException Thrown if the coordinate is not on the game board.
     */
    public int convertCoordinate(int coordinate) throws CoordsOutOfBoundsException {
        try {
            return SyntaxValidator.validateInt(coordinate)
                    .isInRange(0, field.length - 1)
                    .throwIfInvalid("coordinates")
                    .getResult();
        } catch (ValidationException validationException) {
            throw new CoordsOutOfBoundsException(validationException.getMessage());
        }
    }

    /**
     * Converts a single coordinate according to the board used in the game. Implemented by every game board.
     * @param coordinate The coordinate that shall be converted.
     * @param gameModeDependent Whether the coordinate conversion should take the game mode into consideration.
     *                          If false, the standard conversion takes place, only allowing values between 0 and N - 1.
     * @return Returns the converted coordinate.
     * @throws CoordsOutOfBoundsException Thrown if the coordinate is outside the game board.
     */
    public abstract int convertCoordinate(int coordinate, boolean gameModeDependent) throws CoordsOutOfBoundsException;


    /* ****** ACTIONS ****** */

    /**
     * Tries to place a player on a position on the board.
     * @param player The player-number that shall be placed on the given position.
     * @param position The position where the player wants to make his/mer move.
     * @throws PosOccupiedException Thrown if there is already a player on this field.
     * @throws CoordsOutOfBoundsException Thrown if the position is outside the game board.
     */
    public void tryPlace(int player, Position position) throws PosOccupiedException, CoordsOutOfBoundsException {
        Position convertedPosition = convertPosition(position);
        int state = getState(convertedPosition);

        if (state != 0) { // position already occupied
            throw new PosOccupiedException(convertedPosition.getRow(), convertedPosition.getCol(), state);
        }

        place(player, convertedPosition);
    }

    /**
     * Actually places a number on the game board.
     * @param value The number that shall be placed.
     * @param position The position where the value shall be placed.
     */
    private void place(int value, Position position) {
        this.field[position.getRow()][position.getCol()] = value;
    }

    /**
     * Finds lines on the game board that go through a given position, having a given length.
     * This method is used to determine if a move that has been made completed a line that is 6 fields long,
     * leading to winning the game.
     * @param includingPosition The Position the lines shall include.
     * @param lineLength The number of connected fields that shall be searched.
     * @return Returns true if a line that matches the requirements was found.
     * @throws CoordsOutOfBoundsException Thrown if the given includingPosition is not a valid Position on the board.
     */
    public boolean findRayLines(Position includingPosition, int lineLength) throws CoordsOutOfBoundsException {
        return lineFinder.findLines(includingPosition, lineLength);
    }

    /**
     * Resets the whole game board
     */
    public void reset() {
        this.field = new int[this.field.length][this.field.length];
    }

    /**
     * Resets a single position on the game board
     * @param point The position that shall be reset
     */
    public void reset(Position point) {
        this.place(0, point);
    }


    /* ****** GETTERS ****** */

    /**
     * Used to generate a game board that fulfills the requirements of the passed game options.
     * @param gameOptions The game options that contain the information about the required game board.
     * @return Returns a game board that fulfills the requirements of the passed game options.
     * @throws InvalidGameOptionsException Thrown if the provided game options are incorrect.
     */
    public static GameBoard getNewBoard(GameOptions gameOptions) throws InvalidGameOptionsException {
        if (gameOptions == null)
            throw new InvalidGameOptionsException("please enter game options.");

        switch (gameOptions.getGameMode()) {
            case "standard":
                return new StandardBoard(gameOptions);
            case "torus":
                return new TorusBoard(gameOptions);
            default:
                throw new InvalidGameOptionsException("unable to create game board.");
        }
    }

    /**
     * Gets the game boards field size.
     * @return The field size of the game board.
     */
    public int getFieldSize() {
        return this.field.length;
    }

    /**
     * Gets the state of the game board at a given position.
     * E.g.: 1 if player 1 placed a stone here before, 0 if the field is still empty at this position.
     * @param position The position on the game board that shall be checked.
     * @return The state the game board at the given position.
     * @throws CoordsOutOfBoundsException thrown if the position is out of the bounds of the game board.
     */
    public int getState(Position position) throws CoordsOutOfBoundsException {
        Position convertedPoint = convertPosition(position);
        return this.field[convertedPoint.getRow()][convertedPoint.getCol()];
    }

    /**
     * Used to recieve a whole row of the game board.
     * @param rowId The id of the row that shall be returned.
     * @return Returns all the values in the given row.
     * @throws CoordsOutOfBoundsException Thrown if the row is not on the game board, independent of the game mode.
     */
    public int[] getRow(int rowId) throws CoordsOutOfBoundsException {
        return this.field[convertCoordinate(rowId, false)];
    }

    /**
     * Used to receive a whole column of the game board. Works
     * @param colId The id of the column that shall be returned.
     * @return Returns all the values in the given column.
     * @throws CoordsOutOfBoundsException Thrown if the column is not on the game board, independent of the game mode.
     */
    public int[] getCol(int colId) throws CoordsOutOfBoundsException {
        int newColId = convertCoordinate(colId, false);
        int[] col = new int[getFieldSize()];
        for (int i = 0; i < getFieldSize(); i++) { // Loop through every row
            col[i] = this.field[i][newColId];
        }

        return col;
    }

    /* ****** SETTERS ****** */

    /**
     * Checks whether a given position is outside the game board.
     * @param position The position that shall be checked.
     * @return Return true if the position is on the game board.
     */
    public abstract boolean isOutsideBoard(Position position);

    /**
     * Overrides the field of the game board with a new one.
     * @param field The new field of the game board.
     */
    public void setField(int[][] field) {
        this.field = field;
    }

    /**
     * Overrides the line finder of the game board with a new one.
     * @param lineFinder The new line finder of the game board.
     */
    public void setLineFinder(LineFinder lineFinder) {
        this.lineFinder = lineFinder;
    }
}