import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ms.Game;
import ms.MineField;

public class GameTests {

    private Game game;
    private final int mines = 10;

    @BeforeEach
    void setup() {
        game = new Game(10,10, mines);
    }

    @Test
    void testGameInitialization() {
        MineField voidMinefield = new MineField(10, 10, 0);
        assertEquals(voidMinefield, game.getMinefield()); // Ensure minefield empty
        assertEquals(mines, game.getMinesLeft()); // Default mines left
        assertFalse(game.getGameOver());
        assertEquals(0, game.getRevealed());
        assertEquals(10 * 10, game.getUnrevealedCount());
    }

    @Test
    void testGameInitializationWithEasyParameters() {
        game = new Game(9, 9, mines);
        game.revealCell(0,0);
        assertEquals(9, game.getMinefield().getHeight());
        assertEquals(9, game.getMinefield().getWidth());
        assertEquals(mines, game.getMinefield().getMines());
    }

    @Test
    void testGameInitializationWithMediumParameters() {
        game = new Game(16, 16, 40);
        game.revealCell(0,0);
        assertEquals(16, game.getMinefield().getHeight());
        assertEquals(16, game.getMinefield().getWidth());
        assertEquals(40, game.getMinefield().getMines());
    }

    @Test
    void testGameInitializationWithHardParameters() {
        game = new Game(16, 30, 99);
        game.revealCell(0,0);
        assertEquals(16, game.getMinefield().getHeight());
        assertEquals(30, game.getMinefield().getWidth());
        assertEquals(99, game.getMinefield().getMines());
    }

    @Test
    void testMinefieldInitializationOnFirstClick() {
        game.revealCell(0,0);
        assertEquals(10, game.getMinefield().getHeight());
        assertEquals(10, game.getMinefield().getWidth());
        assertEquals(mines, game.getMinefield().getMines());
    }

    @Test
    void testRevealCell() {
        game.revealCell(3, 3);

        assertTrue(game.getMinefield().getCell(3,3).isRevealed());
        assertFalse(game.getMinefield().getCell(0,0).isRevealed());
        assertEquals(1, game.getRevealed());
    }

    @Test
    void testRevealAlreadyRevealedCell() {
        // First reveal
        game.revealCell(0, 0);
        int initialRevealedCount = game.getRevealed();

        // Try to reveal the same cell again
        game.revealCell(0, 0);
        assertTrue(game.getMinefield().getCell(0, 0).isRevealed());
        assertEquals(initialRevealedCount, game.getRevealed());
    }

    @Test
    void testFlagCell() {
        game.revealCell(0,0);

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
        game.revealCell(0, 0);

        assertEquals(mines, game.getMinesLeft()); // Initially, mines left should equal total mines

        game.flagCell(1, 1); // Flag a cell
        assertEquals(mines - 1, game.getMinesLeft());

        game.flagCell(0, 1); // Flag another cell
        assertEquals(mines - 2, game.getMinesLeft());

        game.flagCell(1, 1); // Unflag the first cell
        assertEquals(mines - 1, game.getMinesLeft());
    }
    @Test
    void testRevealMineEndsGame() {
        game.revealCell(0, 0);

        game.getMinefield().getCell(1, 1).setMined(true);
        game.revealCell(1, 1);
        assertTrue(game.getGameOver(), "Game should end when a mine is revealed");
    }

    @Test
    void testGameOverWhenAllCellsRevealed() {
        game.revealCell(0, 0);

        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                if (!game.getMinefield().getCell(row, col).isMined()) {
                    game.revealCell(row, col);
                }
            }
        }
        assertTrue(game.getGameOver(), "Game should end when all non-mine cells are revealed");
    }

    @Test
    void testTimerStartsOnFirstReveal() throws InterruptedException {
        assertEquals(0, game.getElapsedTime(), "Initial elapsed time should be 0");

        // Reveal a non-mine cell
        game.revealCell(0, 0);
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                if (!game.getMinefield().getCell(row, col).isMined()) {
                    game.revealCell(row, col);
                    Thread.sleep(10); // Give some time to elapse
                    assertTrue(game.getElapsedTime() > 0, "Timer should start after first reveal");
                    return; // Exit after the first reveal
                }
            }
        }
        fail("Could not find a non-mine cell to reveal for the test.");
    }

    @Test
    void testTimerStopsOnGameLose() throws InterruptedException {
        game.revealCell(0, 0);
        game.getMinefield().getCell(0, 1).setMined(true);
        game.getMinefield().getCell(1, 1).setMined(false);

        //to trigger the time start before revealing a mine
        game.revealCell(1, 1);


        game.revealCell(0, 1);

        assertTrue(game.getGameOver(), "Game should be over after revealing a mine");
        long endTime = game.getElapsedTime();
        Thread.sleep(10);
        assertEquals(endTime, game.getElapsedTime(), "Timer should stop on game over (lose)");
    }

    @Test
    void testTimerStopsOnGameWin() throws InterruptedException {
        game.revealCell(0, 0);

        // Reveal all non-mine cells
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                if (!game.getMinefield().getCell(row, col).isMined()) {
                    game.revealCell(row, col);
                }
            }
        }
        assertTrue(game.getGameOver(), "Game should be over after revealing all non-mine cells");
        long endTime = game.getElapsedTime();
        Thread.sleep(10);
        assertEquals(endTime, game.getElapsedTime(), "Timer should stop on game over (win)");
    }

    @Test
    void testResetGame() throws InterruptedException {
        game.revealCell(0, 0);
        // Set up the game state
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                game.getMinefield().getCell(row, col).setMined(false);
            }
        }
        game.getMinefield().getCell(0, 1).setMined(true);
        game.revealCell(0, 0);
        game.flagCell(1, 1);
        Thread.sleep(50);

        // Trigger game over
        game.revealCell(0, 1);

        assertTrue(game.getGameOver(), "Game should be over before reset");
        assertTrue(game.getElapsedTime() > 0, "Timer should have started");
        assertEquals(1, game.getFlagsPlaced(), "Flags should have been placed");
        assertTrue(game.getMinefield().getCell(0, 0).isRevealed(), "A cell should be revealed");

        game.resetGame();

        assertFalse(game.getGameOver(), "Game should not be over after reset");
        assertEquals(0, game.getElapsedTime(), "Timer should be reset");
        assertEquals(0, game.getFlagsPlaced(), "Flags placed should be reset");
        assertEquals(mines, game.getMinesLeft(), "Mines left should be reset");

        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                assertFalse(game.getMinefield().getCell(row, col).isRevealed(), "All cells should be covered after reset");
                assertFalse(game.getMinefield().getCell(row, col).isFlagged(), "All cells should be unflagged after reset");
            }
        }
    }
}



