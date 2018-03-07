package edu.kit.informatik.game;

import edu.kit.informatik.constructs.math.IntRange;

/**
 * Used as a blueprint for game option objects and for storing general config data.
 */
public class GameOptions {
    /**
     * The valid game modes.
     */
    public static String[] allowedGameModes = {"standard", "torus"};

    /**
     * The allowed integer range for the game board size.
     */
    public static IntRange allowedFieldSize = new IntRange(18, 20);

    /**
     * The allowed integer range for the player count.
     */
    public static IntRange allowedPlayerCount = new IntRange(2, 4);

    /**
     * The game options object parameters.
     */
    private String gameMode;
    private int fieldSize;
    private int playerCount;

    /**
     * Instanciates a new game options object.
     * @param gameMode The desired game mode (see GameOptions class).
     * @param fieldSize The size of the game board.
     * @param playerCount The number of players.
     */
    public GameOptions(String gameMode, int fieldSize, int playerCount) {
        this.gameMode = gameMode;
        this.fieldSize = fieldSize;
        this.playerCount = playerCount;
    }

    /**
     * Gets the game mode.
     * @return The game mode.
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Gets the field size.
     * @return The field size.
     */
    public int getFieldSize() {
        return fieldSize;
    }

    /**
     * Gets the player count.
     * @return The player count.
     */
    public int getPlayerCount() {
        return playerCount;
    }
}