package ms.logic;

import ms.model.GridDimension;
import ms.model.MineField;

/**
 * The {@code MineFieldFactory} interface defines a method for creating instances of {@code MineField}.
 */
@FunctionalInterface
public interface MineFieldFactory {

    MineField createMineField(GridDimension dimensions, int mines);
}
