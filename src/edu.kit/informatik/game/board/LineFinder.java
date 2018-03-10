package edu.kit.informatik.game.board;

import edu.kit.informatik.constructs.specific.Direction;
import edu.kit.informatik.constructs.specific.Position;
import edu.kit.informatik.exceptions.CoordsOutOfBoundsException;

import static edu.kit.informatik.constructs.specific.Direction.*;

/**
 * Used as a helper that is able to search for linear structures (lines) of a certain length in a given field.
 */
public class LineFinder {
    private GameBoard gameBoard;
    private Direction[][] horizontal = {{LEFT}, {RIGHT}};
    private Direction[][] vertical = {{UP}, {DOWN}};
    private Direction[][] diagonal = {{UP, LEFT}, {DOWN, RIGHT}};
    private Direction[][] antidiagonal = {{DOWN, LEFT}, {UP, RIGHT}};

    /**
     * Instantiates a new Line Finder to find lines on the game board.
     * @param gameBoard The game board that shall be searched for lines.
     */
     public LineFinder(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Finds lines of a specific length that go through the position "including".
     * @param including The position on the board where the search should start.
     * @param lineLength The length of lines that shall be searched for.
     * @return Return whether there are lines of the given length including the given point.
     * @throws CoordsOutOfBoundsException Thrown if the given position is not on the board.
     */

    public boolean findLines(Position including, int lineLength) throws CoordsOutOfBoundsException {
        return findLine(including, lineLength, horizontal)
                || findLine(including, lineLength, vertical)
                || findLine(including, lineLength, diagonal)
                || findLine(including, lineLength, antidiagonal);
    }

    /**
     * Finds a line of a specific length that goes through the position "including" in a specific direction
     * @param including The position the line that is being searched for shall include.
     * @param len The length the line shall have at least.
     * @param dirs The set of directions that shall be checked.
     * @return Return if there is a straight line that goes through the position and has the wanted length.
     * @throws CoordsOutOfBoundsException Thrown if the given position is not on the board.
     */
    public boolean findLine(Position including, int len, Direction[][] dirs) throws CoordsOutOfBoundsException {
        int connectedFields = 1;

        Position startingPosition = including.copy();
        Position nextPosition = including.copy();

        int player = gameBoard.getState(including);

        for (Direction[] directions : dirs) {
            while (connectedFields < len
                    && !gameBoard.isOutsideBoard(nextPosition.moveInDirections(directions))
                    && gameBoard.getState(nextPosition) == player) {
                connectedFields++;
            }
            nextPosition = startingPosition;
        }

        /* Return true also if the found line is longer than len. If with a move a player completes a line that is 7
        * fields long (e.g. converting "P1 P1 P1 ** P1 P1 P1 P1" to "P1 P1 P1 P1 P1 P1 P1 P1"), this shall still
        * count as a win. */
        return connectedFields >= len;
    }
}