package ms.logic;

import ms.model.GridDimension;
import ms.model.MineField;

public class DefaultMineFieldFactory implements MineFieldFactory {

    @Override
    public MineField createMineField(GridDimension dimensions, int mines) {
        return new MineField(dimensions, mines);
    }
}