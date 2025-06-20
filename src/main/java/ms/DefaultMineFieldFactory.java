package ms;

public class DefaultMineFieldFactory implements MineFieldFactory {

    @Override
    public MineField createMineField(GridDimension dimensions, int mines) {
        return new MineField(dimensions, mines);
    }
}