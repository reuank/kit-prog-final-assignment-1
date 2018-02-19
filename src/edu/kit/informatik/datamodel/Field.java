package edu.kit.informatik.datamodel;

public class Field {
    private int[][] field;

    public Field(int size) {
        this.field = new int[size][size];
    }

    public int[][] getArray() {
        return field;
    }

    public boolean setValue(int row, int col, int player) {
        if(getValue(row, col) != 0) {
            return false;
        } else {
            this.field[row][col] = player;
            return true;
        }
    }

    public int getValue(int row, int col) {
        return this.field[row][col];
    }

    public void reset() {
        int fieldSize = field.length;
        this.field = new int[fieldSize][fieldSize];
    }
}