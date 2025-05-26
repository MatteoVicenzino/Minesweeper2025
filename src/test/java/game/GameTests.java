package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import ms.Game;
import ms.MineField;
import ms.MineFieldFactory;

public class GameTests {

    private Game game;
    private final int mines = 10;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        game = new Game(10, 10, mines);
    }

    @Test
    void testGameInitialization() {
        MineField voidMinefield = new MineField(10, 10, 0);
        assertEquals(voidMinefield, game.getMinefield());
        assertEquals(mines, game.getMinesLeft());
        assertFalse(game.getGameOver());
        assertEquals(0, game.getRevealed());
        assertEquals(10 * 10, game.getUnrevealedCount());
    }

    @Test
    void testGameInitializationWithEasyParameters() {
        game = new Game(9, 9, mines);
        game.revealCell(0, 0);
        assertEquals(9, game.getMinefield().getHeight());
        assertEquals(9, game.getMinefield().getWidth());
        assertEquals(mines, game.getMinefield().getMines());
    }

    @Test
    void testGameInitializationWithMediumParameters() {
        game = new Game(16, 16, 40);
        game.revealCell(0, 0);
        assertEquals(16, game.getMinefield().getHeight());
        assertEquals(16, game.getMinefield().getWidth());
        assertEquals(40, game.getMinefield().getMines());
    }

    @Test
    void testGameInitializationWithHardParameters() {
        game = new Game(16, 30, 99);
        game.revealCell(0, 0);
        assertEquals(16, game.getMinefield().getHeight());
        assertEquals(30, game.getMinefield().getWidth());
        assertEquals(99, game.getMinefield().getMines());
    }

    @Test
    void testMinefieldInitializationOnFirstClick() {
        game.revealCell(0, 0);
        assertEquals(10, game.getMinefield().getHeight());
        assertEquals(10, game.getMinefield().getWidth());
        assertEquals(mines, game.getMinefield().getMines());
    }

    @Test
    void testRevealCell() {
        boolean[][] minePattern = {
                {false, false, false, false},
                {false, false, false, false},
                {false, false, true, false},
                {false, false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(4, 4, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(4, 4, 1)).thenReturn(testMineField);

        game.revealCell(3, 3);

        assertTrue(game.getMinefield().getCell(3,3).isRevealed());
        assertFalse(game.getMinefield().getCell(0,0).isRevealed());
        assertEquals(1, game.getRevealed());
    }

    @Test
    void testRevealAlreadyRevealedCell() {
        game.revealCell(0, 0);
        int initialRevealedCount = game.getRevealed();

        game.revealCell(0, 0);
        assertTrue(game.getMinefield().getCell(0, 0).isRevealed());
        assertEquals(initialRevealedCount, game.getRevealed());
    }

    @Test
    void testFlagCell() {
        boolean[][] minePattern = {
                {false, true, false, false, false},
                {true, false, false, false, false},
                {false, false, true, false, false},
                {false, false, false, true, false},
                {false, false, false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(5, 5, 4, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(5, 5, 4)).thenReturn(testMineField);

        game.revealCell(4, 4);

        assertEquals(0, game.getFlagsPlaced());

        game.flagCell(1, 1);
        assertTrue(game.getMinefield().getCell(1, 1).isFlagged());
        assertEquals(1, game.getFlagsPlaced());

        game.flagCell(1, 1);
        assertFalse(game.getMinefield().getCell(1, 1).isFlagged());
        assertEquals(0, game.getFlagsPlaced());

        game.revealCell(3, 4);
        game.flagCell(3, 4);
        assertFalse(game.getMinefield().getCell(3, 4).isFlagged());
        assertEquals(0, game.getFlagsPlaced());
    }

    @Test
    void testGetMinesLeft() {
        boolean[][] minePattern = {
                {false, false, false, false},
                {false, true, false, false},
                {false, false, true, false},
                {false, false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(4, 4, 2, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(4, 4, 2)).thenReturn(testMineField);

        game.revealCell(0, 0);

        assertEquals(2, game.getMinesLeft());

        game.flagCell(1, 1);
        assertEquals(1, game.getMinesLeft());

        game.flagCell(0, 1);
        assertEquals(0, game.getMinesLeft());

        game.flagCell(1, 1);
        assertEquals(1, game.getMinesLeft());
    }

    @Test
    void testRevealMineEndsGame() {
        boolean[][] minePattern = GameTestsHelper.createSimpleCenterMinePattern();
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(3, 3, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(3, 3, 1)).thenReturn(testMineField);

        game.revealCell(1, 1);
        assertTrue(game.getGameOver(), "Game should end when a mine is revealed");
    }

    @Test
    void testGameOverWhenAllCellsRevealed() {
        game.revealCell(0, 0);
        GameTestsHelper.revealAllNonMineCells(game);
        assertTrue(game.getGameOver(), "Game should end when all non-mine cells are revealed");
    }

    @Test
    void testTimerStartsOnFirstReveal() throws InterruptedException {
        assertEquals(0, game.getElapsedTime(), "Initial elapsed time should be 0");

        game.revealCell(0, 0);
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                if (!game.getMinefield().getCell(row, col).isMined()) {
                    game.revealCell(row, col);
                    Thread.sleep(10);
                    assertTrue(game.getElapsedTime() > 0, "Timer should start after first reveal");
                    return;
                }
            }
        }
        fail("Could not find a non-mine cell to reveal for the test.");
    }

    @Test
    void testTimerStopsOnGameLose() throws InterruptedException {
        boolean[][] minePattern = {
                {false, true, false},
                {false, false, false},
                {false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(3, 3, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(3, 3, 1)).thenReturn(testMineField);

        game.revealCell(0, 1);

        assertTrue(game.getGameOver(), "Game should be over after revealing a mine");
        long endTime = game.getElapsedTime();
        Thread.sleep(10);
        assertEquals(endTime, game.getElapsedTime(), "Timer should stop on game over (lose)");
    }

    @Test
    void testTimerStopsOnGameWin() throws InterruptedException {
        game.revealCell(0, 0);
        GameTestsHelper.revealAllNonMineCells(game);
        assertTrue(game.getGameOver(), "Game should be over after revealing all non-mine cells");
        long endTime = game.getElapsedTime();
        Thread.sleep(10);
        assertEquals(endTime, game.getElapsedTime(), "Timer should stop on game over (win)");
    }

    @Test
    void testResetGame() throws InterruptedException {
        boolean[][] minePattern = GameTestsHelper.createSimpleCenterMinePattern();
        game = GameTestsHelper.createGameWithMockFactory(3, 3, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(3, 3, 0)).thenAnswer(invocation -> new MineField(3, 3, 0));
        when(mockMineFieldFactory.createMineField(3, 3, 1)).thenAnswer(invocation -> GameTestsHelper.createMineFieldWithPattern(minePattern));

        game.revealCell(0, 0);

        // Set up game state before reset
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                game.getMinefield().getCell(row, col).setMined(false);
            }
        }
        game.getMinefield().getCell(0, 1).setMined(true);
        game.revealCell(0, 0);
        game.flagCell(1, 1);
        Thread.sleep(50);

        game.revealCell(0, 1);

        assertTrue(game.getGameOver(), "Game should be over before reset");
        assertTrue(game.getElapsedTime() > 0, "Timer should have started");
        assertEquals(1, game.getFlagsPlaced(), "Flags should have been placed");
        assertTrue(game.getMinefield().getCell(0, 0).isRevealed(), "A cell should be revealed");

        game.resetGame();

        assertFalse(game.getGameOver(), "Game should not be over after reset");
        assertEquals(0, game.getElapsedTime(), "Timer should be reset");
        assertEquals(0, game.getFlagsPlaced(), "Flags placed should be reset");
        assertEquals(1, game.getMinesLeft(), "Mines left should be reset");

        assertTrue(GameTestsHelper.verifyInitialCellStates(game), "All cells should be in initial state after reset");
    }

    @Test
    void testRevealEmptyCellsCascade() throws InterruptedException {
        boolean[][] minePattern = GameTestsHelper.createCascadeTestPattern();
        MineField realMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        MineField spyMineField = spy(realMineField);

        doNothing().when(spyMineField).initializeGrid(anyInt(), anyInt());

        when(mockMineFieldFactory.createMineField(5, 5, 0)).thenReturn(new MineField(5, 5, 0));
        when(mockMineFieldFactory.createMineField(5, 5, 8)).thenReturn(spyMineField);

        game = GameTestsHelper.createGameWithMockFactory(5, 5, 8, mockMineFieldFactory);

        game.revealCell(0, 0);

        assertFalse(game.getMinefield().getCell(1, 0).isRevealed(), "Cell (1, 0) should not be revealed");
        assertFalse(game.getMinefield().getCell(0, 1).isRevealed(), "Cell (0, 1) should not be revealed");
        assertFalse(game.getMinefield().getCell(1, 1).isRevealed(), "Cell (1, 1) should not be revealed");

        game.revealCell(0, 4);
        assertTrue(game.getMinefield().getCell(0, 3).isRevealed(), "Cell (0, 3) should be revealed");
        assertTrue(game.getMinefield().getCell(1, 3).isRevealed(), "Cell (1, 3) should be revealed");
        assertTrue(game.getMinefield().getCell(1, 4).isRevealed(), "Cell (1, 4) should be revealed");

        game.revealCell(4, 4);
        assertFalse(game.getMinefield().getCell(3, 4).isRevealed(), "Cell (3, 4) should not be revealed");
        assertFalse(game.getMinefield().getCell(3, 3).isRevealed(), "Cell (3, 3) should not be revealed");
        assertFalse(game.getMinefield().getCell(4, 3).isRevealed(), "Cell (4, 3) should not be revealed");

        game.revealCell(4, 0);
        assertTrue(game.getMinefield().getCell(4, 1).isRevealed(), "Cell (4, 1) should be revealed");
        assertTrue(game.getMinefield().getCell(4, 2).isRevealed(), "Cell (4, 2) should be revealed");
        assertFalse(game.getMinefield().getCell(4, 3).isRevealed(), "Cell (4, 3) should not be revealed");

        assertTrue(game.getMinefield().getCell(3, 0).isRevealed(), "Cell (3, 0) should be revealed");
        assertTrue(game.getMinefield().getCell(3, 1).isRevealed(), "Cell (3, 1) should be revealed");
        assertTrue(game.getMinefield().getCell(3, 2).isRevealed(), "Cell (3, 2) should be revealed");
        assertFalse(game.getMinefield().getCell(3, 3).isRevealed(), "Cell (3, 3) should not be revealed");

        assertTrue(game.getMinefield().getCell(2, 0).isRevealed(), "Cell (2, 0) should be revealed");
        assertTrue(game.getMinefield().getCell(2, 1).isRevealed(), "Cell (2, 1) should be revealed");
        assertFalse(game.getMinefield().getCell(2, 2).isRevealed(), "Cell (2, 2) should not be revealed");

        assertFalse(game.getMinefield().getCell(1, 0).isRevealed(), "Cell (1, 0) should not be revealed");
    }
}



