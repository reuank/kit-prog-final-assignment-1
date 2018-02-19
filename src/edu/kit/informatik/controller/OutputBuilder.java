package edu.kit.informatik.controller;

import edu.kit.informatik.datamodel.Field;
import edu.kit.informatik.datamodel.Game;
import edu.kit.informatik.datamodel.GameOptions;

public class OutputBuilder {

    // Build row
    // Build col
    // Build field
    public String buildGameParams(Game game) {
        GameOptions gameOptions = game.getGameOptions();
        String output = String.format(
                "Gamemode: %s, FieldSize: %d, PlayerCount: %d",
                gameOptions.getGameMode(),
                gameOptions.getFieldSize(),
                gameOptions.getPlayerCount()
        );

        return output;
    }

    public String buildRow(Game game, int rowId) {
        StringBuilder rowRepresentation = new StringBuilder();
        int[] row = game.getFieldAsArray()[rowId];
        for (int i = 0; i < row.length; i++) {
            int value = row[i];
            rowRepresentation.append(value == 0 ? "**" : "P" + value);
            rowRepresentation.append(i < row.length - 1 ? " " : "");
        }

        return rowRepresentation.toString();
    }

    public String buildField(Game game) {
        StringBuilder fieldRepresentation = new StringBuilder();
        Field field = game.getField();
        int fieldSize = field.getArray().length;

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                int value = field.getValue(i, j);
                fieldRepresentation.append(value == 0 ? "**" : "P" + value);
                fieldRepresentation.append(j < fieldSize - 1 ? " " : "");
            }
            fieldRepresentation.append(i < fieldSize - 1 ? "\n" : "");
        }

        return fieldRepresentation.toString();
    }
}