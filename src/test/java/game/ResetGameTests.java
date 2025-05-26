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

public class ResetGameTests {

    private Game game;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testResetGameClearsGameOverStatus() throws InterruptedException {

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

        game.resetGame();

        assertFalse(game.getGameOver(), "Game should not be over after reset");
    }

    @Test
    void testResetGameClearsTimer() throws InterruptedException {

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

        assertTrue(game.getElapsedTime() > 0, "Timer should have started");

        game.resetGame();

        assertEquals(0, game.getElapsedTime(), "Timer should be reset");
    }

    @Test
    void testResetGameClearsFlagsPlaced() throws InterruptedException {

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

        assertEquals(1, game.getFlagsPlaced(), "Flags should have been placed");

        game.resetGame();

        assertEquals(0, game.getFlagsPlaced(), "Flags placed should be reset");
    }

    @Test
    void testResetGameRestoresMinesLeft() throws InterruptedException {

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

        game.resetGame();

        assertEquals(1, game.getMinesLeft(), "Mines left should be reset");
    }

    @Test
    void testResetGameRestoresInitialCellStates() throws InterruptedException {

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

        assertTrue(game.getMinefield().getCell(0, 0).isRevealed(), "A cell should be revealed");

        game.resetGame();

        assertTrue(GameTestsHelper.verifyInitialCellStates(game), "All cells should be in initial state after reset");
    }
}

