package ms;

@FunctionalInterface
public interface MineFieldFactory {
    MineField createMineField(int height, int width, int mines);
}
