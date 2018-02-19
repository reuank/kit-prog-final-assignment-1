package edu.kit.informatik.datamodel;

public class Game {
    private Field field;
    private GameOptions gameOptions;

    // Macht es Sinn, die Spiel-Optionen als eigene Klasse zu definieren?
    public Game(GameOptions gameOptions) {
        this.gameOptions = gameOptions;
        this.field = new Field(gameOptions.getFieldSize());
        // CURRENT PLAYER?
    }

    public Field getField() {
        return field;
    }

    public int[][] getFieldAsArray() {
        return field.getArray();
    }

    public GameOptions getGameOptions() {
        return gameOptions;
    }

    public void reset(){
        this.field = new Field(gameOptions.getFieldSize());
    }
}