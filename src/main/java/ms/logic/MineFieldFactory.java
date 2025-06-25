package ms.logic;

import ms.model.GridDimension;
import ms.model.MineField;

/**
 * The {@code DefaultMineFieldFactory} class implements the {@code MineFieldFactory} interface
 * to create instances of {@code MineField} with specified dimensions and mine count.
 */
@FunctionalInterface
public interface MineFieldFactory {

    MineField createMineField(GridDimension dimensions, int mines);
}
