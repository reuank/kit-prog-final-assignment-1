package edu.kit.informatik.constructs.specific;

import static edu.kit.informatik.constructs.specific.Direction.*;

/**
 * This class is used for storing positions as objects. Every position has a row (y-coord.) and a column (x-coord.).
 */
public class Position {
    private int col;
    private int row;

    /**
     * Instantiates a new Position Object.
     * @param row The row the Position has.
     * @param col The col the Position has
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }


    /* ****** ACTIONS ****** */

    /**
     * Moves the Position up by one.
     * @return Returns the updated position.
     */
    public Position moveUp() {
        this.row--;
        return this;
    }

    /**
     * Moves the Position down by one.
     * @return Returns the updated position.
     */
    public Position moveDown() {
        this.row++;
        return this;
    }

    /**
     * Moves the Position left by one.
     * @return Returns the updated position.
     */
    public Position moveLeft() {
        this.col--;
        return this;
    }

    /**
     * Moves the Position right by one.
     * @return Returns the updated position.
     */
    public Position moveRight() {
        this.col++;
        return this;
    }

    /**
     * Moves a Position in the directions that are passed.
     * @param directions The directions the Position shall be moved towards.
     *                   E.g.: {LEFT, UP} moves the point first to the left, then up.
     * @return Returns the updated Position.
     */
    public Position moveInDirections(Direction[] directions) {
        for (Direction direction : directions) {
            moveInDirection(direction);
        }

        return this;
    }

    /**
     * Moves the Position in a single direction.
     * @param direction The direction the Position shall be moved towards.
     *                  E.g.: LEFT moves the point to the left.
     * @return Returns the updated Position.
     */
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


    /* ****** GETTERS ****** */

    /**
     * Gets the row-coordinate of the Position.
     * @return Returns the Positions row-coordinate.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column-coordinate of the Position.
     * @return Returns the Positions column-coordinate.
     */
    public int getCol() {
        return col;
    }

    /**
     * Creates a new Position Instance with the same coordinates.
     * @return Returns the new Position Instance, that is a exact copy of "this".
     */
    public Position copy() {
        return new Position(this.getRow(), this.getCol());
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Position)) {
            return false;
        }

        Position position = (Position) obj;

        return this.row == position.getRow() &&  this.col == position.getCol();
    }


    /* ****** SETTERS ****** */

    /**
     * Sets the row-coordinate of the Position.
     * @param row The new row-coordinate of the Position.
     */
    public void setRow(int row) {
        this.row = row;
    }

     /**
     * Sets the column-coordinate of the Position.
     * @param col The new column-coordinate of the Position.
     */
     public void setCol(int col) {
        this.col = col;
    }


}
