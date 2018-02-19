package edu.kit.informatik.constructs.program;

import javafx.geometry.Pos;

import static edu.kit.informatik.constructs.program.Direction.*;

public class Position {
    private int col;
    private int row;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Position moveUp() {
        this.row--;
        return this;
    }

    public Position moveDown() {
        this.row++;
        return this;
    }

    public Position moveLeft() {
        this.col--;
        return this;
    }

    public Position moveRight() {
        this.col++;
        return this;
    }

    public Position copy() {
        return new Position(this.getRow(), this.getCol());
    }

    public Position moveInDirections(Direction[] directions) {
        for (Direction direction : directions) {
            moveInDirection(direction);
        }

        return this;
    }

    public Position moveInDirection(Direction direction) {
        if (direction == DOWN) {
            return this.moveDown();
        } else if (direction == UP) {
            return this.moveUp();
        } else if (direction == LEFT) {
            return this.moveLeft();
        } else if (direction == RIGHT) {
            return this.moveRight();
        }

        return this;
    }
}
