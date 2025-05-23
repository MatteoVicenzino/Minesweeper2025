package ms;

public class DefaultMineFieldFactory implements MineFieldFactory {
    @Override
    public MineField createMineField(int height, int width, int mines) {
        return new MineField(height, width, mines);
    }
}