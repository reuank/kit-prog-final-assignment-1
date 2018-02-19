package edu.kit.informatik.datamodel;

public class GameOptions {
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