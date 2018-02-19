package edu.kit.informatik.game;

import com.sun.xml.internal.xsom.impl.Ref;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.constructs.list.PositionList;
import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;
import edu.kit.informatik.exceptions.InvalidGameOptionsException;
import edu.kit.informatik.exceptions.PositionOccupiedException;
import edu.kit.informatik.exceptions.ValidationException;
import edu.kit.informatik.game.board.GameBoard;
import edu.kit.informatik.game.board.LineFinder;
import edu.kit.informatik.game.validation.ConnectSixValidator;
import javafx.geometry.Pos;

public class ConnectSix {
    private GameBoard gameBoard;
    private GameOptions gameOptions;
    private PositionList lastMoves;
    private int currentPlayer;
    private int winner; // -1 = not set, 0 = draw, rest: player that won
    private int moveCounter;
    private LineFinder lineFinder;

    public ConnectSix(GameOptions gameOptions) throws InvalidGameOptionsException {
        try {
            this.gameOptions = ConnectSixValidator.validateGameOptions(gameOptions).getResult();
            this.gameBoard = GameBoard.getNewBoard(this.gameOptions);
            this.lastMoves = new PositionList();
            this.currentPlayer = 1;
            this.winner = -1;
            this.moveCounter = 0;
            this.lineFinder = new LineFinder(gameBoard);
        } catch (ValidationException validationException) {
            throw new InvalidGameOptionsException("the passed game options are incorrect. " + validationException.getMessage());
        }
    }

    public void tryPlaceMultiple(PositionList moves) throws PositionOccupiedException, CoordinatesOutOfBoundsException {
        this.lastMoves = new PositionList();
        PositionList.Iterator it = moves.iterator();

        while (it.hasNext()) { // Loop through the moves that should be done
            Position move = it.currentData();
            if (!lastMoves.contains(move)) { // check if the move has already been done in this turn
                this.gameBoard.tryPlace(currentPlayer, move);
                this.lastMoves.add(move);
                it.next();
            } else { // if this move has already been done -> undo all changes and throw exception
                this.undoLastMoves();
                throw new PositionOccupiedException("someone tries to make the same move multiple times!");
            }
        }

        this.moveCounter += lastMoves.size();
    }

    // Checks the impact of the last moves on the game
    public void checkConsequencesOfLastMoves() throws CoordinatesOutOfBoundsException {
        if (isBoardFull()) {
            this.winner = 0; // Draw!
        }

        PositionList.Iterator it = lastMoves.iterator();
        while (it.hasNext() && !this.hasEnded()) {
            Position move = it.currentData();
            if (moveWonGame(move)) {
                this.winner = currentPlayer;
            }

            it.next();
        }
    }

    private boolean moveWonGame(Position move) throws CoordinatesOutOfBoundsException {
        // checkHorizontal
        return lineFinder.findRayLines(move, 6);
    }

    public void undoLastMoves() {
        PositionList.Iterator it = lastMoves.iterator();
        while (it.hasNext()) {
            this.gameBoard.undo(it.currentData());
            it.next();
        }
    }

    public boolean isBoardFull() {
        return moveCounter == gameBoard.getFieldSize() * gameBoard.getFieldSize();
    }

    public boolean isDraw() {
        return winner == 0;
    }

    public boolean hasEnded() {
        return winner > -1; // draw or someone won
    }

    public boolean hasWinner() {
        return winner > 0;
    }

    public int getWinner() {
        return winner;
    }

    public int getState(Position point) throws CoordinatesOutOfBoundsException {
        return this.gameBoard.getState(point);
    }

    public void convertCol(int col) throws CoordinatesOutOfBoundsException {
        this.gameBoard.convertCol(col);
    }

    public void convertRow(int row) throws CoordinatesOutOfBoundsException {
        this.gameBoard.convertRow(row);
    }

    public void reset() {
        this.gameBoard.reset();
        this.currentPlayer = 1;
        this.moveCounter = 0;
        this.winner = -1;
    }

    public void nextPlayer() {
        this.currentPlayer = (this.currentPlayer % gameOptions.getPlayerCount()) + 1;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}