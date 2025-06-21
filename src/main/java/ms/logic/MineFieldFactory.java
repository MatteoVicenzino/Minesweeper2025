package ms.logic;

import ms.model.GridDimension;
import ms.model.MineField;

@FunctionalInterface
public interface MineFieldFactory {

    MineField createMineField(GridDimension dimensions, int mines);
}
