package ms;

public interface MineFieldFactory {
    MineField createMineField(int height, int width, int mines);
}
