package game;

import ms.Game;
import ms.MineField;
import ms.MineFieldFactory;

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
                    mineField.getCell(row, col).setMined(true);
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
                if (!game.getMinefield().getCell(row, col).isMined()) {
                    game.revealCell(row, col);
                }
            }
        }
    }

    public static boolean verifyInitialCellStates(Game game) {
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                if (game.getMinefield().getCell(row, col).isRevealed() ||
                        game.getMinefield().getCell(row, col).isFlagged()) {
                    return false;
                }
            }
        }
        return true;
    }
}
