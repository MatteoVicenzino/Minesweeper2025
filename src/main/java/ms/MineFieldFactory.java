package ms;

@FunctionalInterface
public interface MineFieldFactory {

    MineField createMineField(GridDimension dimensions, int mines);
}
