package edu.kit.informatik.game;

import edu.kit.informatik.constructs.list.PositionList;
import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidGameOptionsException;
import edu.kit.informatik.exceptions.PosOccupiedException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.board.GameBoard;
import edu.kit.informatik.game.board.LineFinder;
import edu.kit.informatik.game.validation.ConnectSixValidator;

public class ConnectSix {
    private GameBoard gameBoard;
    private GameOptions gameOptions;
    private PositionList lastMoves;
    private int currentPlayer;
    private int winner; // -1 = not set, 0 = draw, rest: player that won
    private int moveCounter;
    private LineFinder lineFinder;

    public ConnectSix(GameOptions gameOptions) throws InvalidGameOptionsException {
        this.currentPlayer = 1;
        this.winner = -1;
        this.moveCounter = 0;

        try {
            this.gameOptions = ConnectSixValidator.validateGameOptions(gameOptions).getResult();
            this.gameBoard = GameBoard.getNewBoard(this.gameOptions);
            this.lineFinder = new LineFinder(gameBoard);
        } catch (ValidationException validationException) {
            throw new InvalidGameOptionsException("the passed game options are incorrect. "
                    + validationException.getMessage());
        }
    }

    public void tryPlaceMultiple(PositionList moves) throws PosOccupiedException, CoordsOutOfBoundsException {
        this.lastMoves = new PositionList();
        PositionList.Iterator it = moves.iterator();

        while (it.hasNext()) { // Loop through the moves that should be done
            Position move = gameBoard.convertPosition(it.currentData());
            if (!lastMoves.contains(move)) { // check if the move has already been done in this turn
                this.gameBoard.tryPlace(currentPlayer, move);
                this.lastMoves.add(move);
                it.next();
            } else { // if this move has already been done -> undo all changes and throw exception
                this.undoLastMoves();
                throw new PosOccupiedException("someone tries to make the same move multiple times!");
            }
        }

        this.moveCounter += lastMoves.size();
        this.checkConsequencesOfLastMoves();
    }

    // Checks the impact of the last moves on the game
    private void checkConsequencesOfLastMoves() throws CoordsOutOfBoundsException {
        PositionList.Iterator it = lastMoves.iterator();
        while (it.hasNext() && !this.hasEnded()) {
            Position move = it.currentData();
            if (moveWonGame(move)) {
                this.winner = currentPlayer;
            }

            it.next();
        }

        if (isBoardFull() && !this.hasEnded()) {
            this.winner = 0; // Draw!
        }
    }

    private boolean moveWonGame(Position move) throws CoordsOutOfBoundsException {
        return gameBoard.findRayLines(move, 6);
    }

    /**
     * Resets the positions
     */
    public void undoLastMoves() { // Only converted moves inside here!
        PositionList.Iterator it = lastMoves.iterator();
        while (it.hasNext()) {
            Position position = it.currentData();
            this.gameBoard.reset(position);
            it.next();
        }
    }

    /**
     * Checks if there are any empty fields on the game board.
     * @return Returns true if
     */
    public boolean isBoardFull() {
        return moveCounter == gameBoard.getFieldSize() * gameBoard.getFieldSize();
    }

    /**
     * Checks if the
     * @return
     */
    public boolean isDraw() {
        return winner == 0;
    }

    /**
     * Checks whether the game ended has either been won or ended with a draw
     * @return Return true if the game has ended
     */
    public boolean hasEnded() {
        return winner > -1; // draw or someone won
    }

    /**
     *
     * @return
     */
    public int getWinner() {
        return winner;
    }

    public int getState(Position point) throws CoordsOutOfBoundsException {
        return this.gameBoard.getState(point);
    }

    public int convertCoordinate(int coordinate, boolean gameModeIndependant) throws CoordsOutOfBoundsException {
        if (gameModeIndependant) {
            return this.gameBoard.convertCoordinate(coordinate);
        }

        return this.gameBoard.convertCoordinate(coordinate);
    }

    public void reset() {
        this.gameBoard.reset();
        this.currentPlayer = 1;
        this.moveCounter = 0;
        this.winner = -1;
        this.lastMoves = new PositionList();
    }

    public void nextPlayer() {
        this.currentPlayer = (this.currentPlayer % gameOptions.getPlayerCount()) + 1;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}