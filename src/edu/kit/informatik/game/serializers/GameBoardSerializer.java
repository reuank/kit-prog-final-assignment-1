package edu.kit.informatik.game.serializers;

import edu.kit.informatik.exceptions.CoordinatesOutOfBoundsException;
import edu.kit.informatik.game.board.GameBoard;

public class GameBoardSerializer {
    public static String serialize(GameBoard gameBoard) {
        StringBuilder gameBoardRepresentation = new StringBuilder();
        try {
            for (int i = 0; i < gameBoard.getFieldSize(); i++) {
                gameBoardRepresentation.append(RowSerializer.serialize(gameBoard, i));
                gameBoardRepresentation.append(i < gameBoard.getFieldSize() - 1 ? "\n" : "");
            }
            return gameBoardRepresentation.toString();
        } catch (CoordinatesOutOfBoundsException exception) {
            // will never happen!
        }

        return null;
    }
}
