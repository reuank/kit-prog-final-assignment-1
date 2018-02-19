package edu.kit.informatik.constructs.list;

import edu.kit.informatik.constructs.program.Position;

public class PositionList extends List<Position> {
    @Override
    public boolean contains(Position needle) {
        Iterator it = iterator();
        while (it.hasNext()) {
            if (it.currentData().getCol() == needle.getCol()
                    && it.currentData().getRow() == needle.getRow()) {
                return true;
            }
            it.next();
        }

        return false;
    }
}
