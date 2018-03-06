package edu.kit.informatik.game;

import edu.kit.informatik.constructs.list.PositionList;
import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidGameOptionsException;
import edu.kit.informatik.exceptions.PosOccupiedException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.board.GameBoard;
import edu.kit.informatik.game.validation.ConnectSixValidator;

/**
 * The instance of the game that holds the game board and all the game logic.
 */
public class ConnectSix {
    private GameBoard gameBoard;
    private GameOptions gameOptions;
    private PositionList lastMoves;
    private int currentPlayer;
    private int winner; // -1 = not set, 0 = draw, rest: player that won
    private int moveCounter;

    /**
     * Instanciates a new ConnectSix game if the passed game options are valid.
     * @param gameOptions The passed game options that have been parsed before already.
     * @throws InvalidGameOptionsException Thrown if the passed game options are semantically invalid.
     */
    public ConnectSix(GameOptions gameOptions) throws InvalidGameOptionsException {
        this.currentPlayer = 1;
        this.winner = -1;
        this.moveCounter = 0;

        try {
            this.gameOptions = ConnectSixValidator.validateGameOptions(gameOptions).getResult();
            this.gameBoard = GameBoard.getNewBoard(this.gameOptions);
        } catch (ValidationException validationException) {
            throw new InvalidGameOptionsException("the passed game options are incorrect. "
                    + validationException.getMessage());
        }
    }

    /**
     * Tries to make multiple moves at once.
     * @param moves The list of positions there the current player wants to place himself.
     * @throws PosOccupiedException Thrown if on one of the positions is already occupied by anyone.
     * @throws CoordsOutOfBoundsException Thrown if the coordinates are out of the bounds of the game board.
     */
    public void tryPlaceMultiple(PositionList moves) throws PosOccupiedException, CoordsOutOfBoundsException {
        this.lastMoves = new PositionList();
        PositionList.Iterator it = moves.iterator();

        while (it.hasNext()) { // Loop through the moves that should be done
            Position move = this.gameBoard.convertPosition(it.currentData());
            if (!this.lastMoves.contains(move)) { // check if the move has already been done in this turn
                this.gameBoard.tryPlace(this.currentPlayer, move);
                this.lastMoves.add(move);
                it.next();
            } else { // if this move has already been done -> undo all changes and throw exception
                this.undoLastMoves();
                throw new PosOccupiedException("someone tries to make the same move multiple times!");
            }
        }

        this.moveCounter += this.lastMoves.size();
        this.checkConsequencesOfLastMoves();
    }

    /**
     * Checks the impact of the last moves on the game, e.g. whether a move made the current player win.
     */
    private void checkConsequencesOfLastMoves() {
        PositionList.Iterator it = this.lastMoves.iterator();
        while (it.hasNext() && !this.hasEnded()) {
            Position move = it.currentData();
            if (moveWonGame(move)) {
                this.winner = this.currentPlayer;
            }

            it.next();
        }

        if (isBoardFull() && !this.hasEnded()) {
            this.winner = 0; // Draw!
        }
    }

    /**
     * The function that checks whether a particular move completed a line that causes the game to end.
     * @param move The move that shall be examined.
     * @return Returns true if the move actually made the current player win.
     */
    private boolean moveWonGame(Position move) {
        try {
            return this.gameBoard.findRayLines(move, 6);
        } catch (CoordsOutOfBoundsException exception) {
            throw new IllegalStateException();
        }
    }

    /**
     * Resets the last moves that have been done to state 0.
     */
    public void undoLastMoves() { // Only converted moves inside here!
        PositionList.Iterator it = this.lastMoves.iterator();
        while (it.hasNext()) {
            Position position = it.currentData();
            this.gameBoard.reset(position);
            it.next();
        }
    }

    /**
     * Checks if there are any empty fields on the game board.
     * @return Returns true if the game board is full.
     */
    public boolean isBoardFull() {
        return this.moveCounter == this.gameBoard.getFieldSize() * this.gameBoard.getFieldSize();
    }

    /**
     * Checks if the game has ended with a draw.
     * @return Returns true if the game ended with a draw.
     */
    public boolean isDraw() {
        return this.winner == 0;
    }

    /**
     * Checks whether the game ended has either been won or ended with a draw
     * @return Return true if the game has ended
     */
    public boolean hasEnded() {
        return this.winner > -1; // draw or someone won
    }

    /**
     * Gets the winner of the game.
     * @return The winner of the game. 0 if draw, -1 if not set yet.
     */
    public int getWinner() {
        return this.winner;
    }

    /**
     * Gets the state of a specific position on the game board.
     * @param position The position whose state is of interest.
     * @return Returns the state of the position on the game board. 0 if there is no player.
     * @throws CoordsOutOfBoundsException Thrown if the coordinate is outside the game board.
     */
    public int getState(Position position) throws CoordsOutOfBoundsException {
        return this.gameBoard.getState(position);
    }

    /**
     * Converts a single coordinate according to the board used in the game.
     * @param coordinate The coordinate that shall be converted.
     * @param gameModeDependent Whether the coordinate conversion should take the game mode into consideration.
     *                          If false, the standard conversion takes place, only allowing values between 0 and N - 1.
     * @return Returns the converted coordinate.
     * @throws CoordsOutOfBoundsException Thrown if the coordinate is outside the game board.
     */
    public int convertCoordinate(int coordinate, boolean gameModeDependent) throws CoordsOutOfBoundsException {
        return this.gameBoard.convertCoordinate(coordinate, gameModeDependent);
    }

    /**
     * Resets the entire game and restarts it.
     */
    public void reset() {
        this.gameBoard.reset();
        this.currentPlayer = 1;
        this.moveCounter = 0;
        this.winner = -1;
        this.lastMoves = new PositionList();
    }

    /**
     * Moves to the next player. Goes back to 1 if the last player has finished his move.
     */
    public void nextPlayer() {
        this.currentPlayer = (this.currentPlayer % gameOptions.getPlayerCount()) + 1;
    }

    /**
     * Gets the game board of the game.
     * @return The game board that belongs to the game.
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }
}