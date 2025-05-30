package game;

import ms.Game;
import ms.MineField;
import ms.MineFieldFactory;
import ms.Position;

import static org.mockito.Mockito.when;

public class GameTestsHelper {

    public static Game createGameWithMockFactory(int height, int width, int mines, MineFieldFactory mockFactory) {
        return new Game(height, width, mines, mockFactory);
    }

    public static MineField createMineFieldWithPattern(boolean[][] minePattern) {
        int height = minePattern.length;
        int width = minePattern[0].length;
        int mineCount = countMinesInPattern(minePattern);

        MineField mineField = new MineField(height, width, mineCount);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (minePattern[row][col]) {
                    mineField.getCell(new Position(row, col)).setMined(true);
                }
            }
        }

        return mineField;
    }

    public static int countMinesInPattern(boolean[][] minePattern) {
        int mineCount = 0;
        for (int row = 0; row < minePattern.length; row++) {
            for (int col = 0; col < minePattern[row].length; col++) {
                if (minePattern[row][col]) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    public static boolean[][] createSimpleCenterMinePattern() {
        return new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, false, false}
        };
    }

    public static boolean[][] createCascadeTestPattern() {
        return new boolean[][]{
                {false, false, true, false, false},
                {false, true, true, false, false},
                {false, false, true, true, true},
                {false, false, false, false, true},
                {false, false, false, true, false}
        };
    }

    public static void revealAllNonMineCells(Game game) {
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                if (!game.getMinefield().getCell(new Position(row, col)).isMined()) {
                    game.revealCell(new Position(row, col));
                }
            }
        }
    }

    public static boolean verifyInitialCellStates(Game game) {
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                if (game.getMinefield().getCell(new Position(row, col)).isRevealed() ||
                        game.getMinefield().getCell(new Position(row, col)).isFlagged()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void setupMockFactoryForReset(MineFieldFactory mockFactory,
                                                int height, int width, int mineCount) {
        when(mockFactory.createMineField(height, width, 0))
                .thenAnswer(invocation -> new MineField(height, width, 0));

        when(mockFactory.createMineField(height, width, mineCount))
                .thenAnswer(invocation -> createMineFieldWithPattern(createSimpleCenterMinePattern()));
    }

    public static Game createAndSetupGameForReset(int height, int width, int mineCount,
                                                  MineFieldFactory mockFactory,
                                                  Position safePosition,
                                                  Position minePosition,
                                                  Position flagPosition) throws InterruptedException {
        Game game = createGameWithMockFactory(height, width, mineCount, mockFactory);

        game.revealCell(safePosition);

        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                game.getMinefield().getCell(new Position(row, col)).setMined(false);
            }
        }

        game.getMinefield().getCell(minePosition).setMined(true);

        game.revealCell(safePosition);
        game.flagCell(flagPosition);

        Thread.sleep(50);

        game.revealCell(minePosition);

        return game;
    }
}
