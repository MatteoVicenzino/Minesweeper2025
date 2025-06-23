package game;

import ms.logic.Game;
import ms.logic.MineFieldFactory;
import ms.model.Cell;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameTestsHelper {

    public static Game createGameWithMockFactory(GridDimension dimensions, int mines, MineFieldFactory mockFactory) {
        return new Game(dimensions, mines, mockFactory);
    }

    public static MineField createMineFieldWithPattern(boolean[][] minePattern) {

        GridDimension dimensions = new GridDimension(minePattern[0].length, minePattern.length);
        int mineCount = countMinesInPattern(minePattern);

        MineField mineField = spy(new MineField(dimensions, mineCount));

        doAnswer(invocation -> {
            clearAllMines(mineField);
            applyMinePattern(mineField, minePattern);
            return null;
        }).when(mineField).initializeGrid(any(Position.class));

        applyMinePattern(mineField, minePattern);

        return mineField;
    }

    private static void applyMinePattern(MineField mineField, boolean[][] minePattern) {
        for (int row = 0; row < minePattern.length; row++) {
            for (int col = 0; col < minePattern[row].length; col++) {
                Position pos = new Position(row, col);
                Cell cell = mineField.getCell(pos);
                cell.setMined(minePattern[row][col]);
            }
        }
    }

    private static void clearAllMines(MineField minefield) {
        for (int row = 0; row < minefield.getHeight(); row++) {
            for (int col = 0; col < minefield.getWidth(); col++) {
                Position pos = new Position(row, col);
                minefield.getCell(pos).setMined(false);
            }
        }
    }

    public static int countMinesInPattern(boolean[][] minePattern) {
        int mineCount = 0;
        for (boolean[] booleans : minePattern) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
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
                    try {
                        game.revealCell(new Position(row, col));
                    } catch (Game.InvalidGameOperationException e) {
                        // Ignore exceptions for already revealed cells
                    }
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
                                                GridDimension dimensions, int mineCount) {
        when(mockFactory.createMineField(dimensions, 0))
                .thenAnswer(invocation -> new MineField(dimensions, 0));

        when(mockFactory.createMineField(dimensions, mineCount))
                .thenAnswer(invocation -> createMineFieldWithPattern(createSimpleCenterMinePattern()));
    }

    public static Game createAndSetupGameForReset(GridDimension dimensions, int mineCount,
                                                  MineFieldFactory mockFactory,
                                                  Position safePosition,
                                                  Position minePosition,
                                                  Position flagPosition) throws InterruptedException {
        Game game = createGameWithMockFactory(dimensions, mineCount, mockFactory);

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
