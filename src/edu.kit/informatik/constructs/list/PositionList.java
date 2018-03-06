package edu.kit.informatik.constructs.list;

import edu.kit.informatik.constructs.program.Position;

/**
 * Used to store positions in a list. Really handy for storing moves of a game.
 */
public class PositionList extends List<Position> {
    @Override
    public boolean contains(Position position) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (it.currentData().getCol() == position.getCol()
                    && it.currentData().getRow() == position.getRow()) {
                return true;
            }

            it.next();
        }

        return false;
    }
}
