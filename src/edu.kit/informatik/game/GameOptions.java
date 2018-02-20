package edu.kit.informatik.game;

import edu.kit.informatik.constructs.math.IntRange;

public class GameOptions {
    // General game config Data
    public static String[] allowedGameModes = {"standard", "torus"};
    public static IntRange allowedFieldSize = new IntRange(18, 20);
    public static IntRange allowedPlayerCount = new IntRange(2, 4);

    // GameOptions Object attributes
    private String gameMode;
    private int fieldSize;
    private int playerCount;

    public GameOptions(String gameMode, int fieldSize, int playerCount) {
        this.gameMode = gameMode;
        this.fieldSize = fieldSize;
        this.playerCount = playerCount;
    }

    public String getGameMode() {
        return gameMode;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}