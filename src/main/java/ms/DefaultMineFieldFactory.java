package ms;

public class DefaultMineFieldFactory implements MineFieldFactory {
    @Override
    public MineField createMineField(int height, int width, int mines, int firstRow, int firstCol) {
        return new MineField(height, width, mines);
    }
}
