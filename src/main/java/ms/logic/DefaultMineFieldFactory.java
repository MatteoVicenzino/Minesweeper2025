package ms.logic;

import ms.model.GridDimension;
import ms.model.MineField;

/**
 * The {@code DefaultMineFieldFactory} class implements the {@code MineFieldFactory} interface
 * to create instances of {@code MineField} with specified dimensions and mine count.
 */
public class DefaultMineFieldFactory implements MineFieldFactory {

    @Override
    public MineField createMineField(GridDimension dimensions, int mines) {
        return new MineField(dimensions, mines);
    }
}