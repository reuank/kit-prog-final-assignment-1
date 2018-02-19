package edu.kit.informatik.game.board;

import edu.kit.informatik.constructs.program.Direction;
import edu.kit.informatik.constructs.program.Position;
import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;

import static edu.kit.informatik.constructs.program.Direction.*;

public class LineFinder {
    private GameBoard gameBoard;
    private Direction[][] horizontalDirections = {{LEFT}, {RIGHT}};
    private Direction[][] verticalDirections = {{UP}, {DOWN}};
    private Direction[][] diagonalDirections = {{UP, LEFT}, {DOWN, RIGHT}};
    private Direction[][] antidiagonalDirections = {{DOWN, LEFT}, {UP, RIGHT}};

    public LineFinder(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public boolean findRayLines(Position beginAt, int maxLength) throws CoordinatesOutOfBoundsException {
        return findLine(beginAt, maxLength, horizontalDirections)
                || findLine(beginAt, maxLength, verticalDirections)
                || findLine(beginAt, maxLength, diagonalDirections)
                || findLine(beginAt, maxLength, antidiagonalDirections);
    }


    public boolean findLine(Position beginAt, int maxLength, Direction[][] bothDirections) throws CoordinatesOutOfBoundsException {
        int connectedFields = 1;

        Position startingPosition = beginAt.copy();
        Position nextPosition = beginAt.copy();

        int needle = gameBoard.getState(beginAt);

        for (Direction[] directions : bothDirections) {
            while (connectedFields < maxLength
                    && !gameBoard.isWall(nextPosition.moveInDirections(directions))
                    && gameBoard.getState(nextPosition) == needle) {
                connectedFields++;
            }
            nextPosition = startingPosition;
        }

        return connectedFields == maxLength;
    }
}