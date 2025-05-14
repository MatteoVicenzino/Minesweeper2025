import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ms.Game;

public class GameTests {

    private Game game;
    private final int mines = 10;

    @BeforeEach
    void setup() {
        game = new Game();
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game.getMinefield()); // Ensure minefield is null before generation
        assertEquals(10, game.getMinesLeft()); // Default mines left
        assertFalse(game.getGameOver());
    }

    @Test
    void testMinefieldInitializationOnGameCreation() {
        assertEquals(10, game.getMinefield().getHeight());
        assertEquals(10, game.getMinefield().getWidth());
        assertEquals(10, game.getMinefield().getMines());
    }

    @Test
    void testRevealCell() {
        game.revealCell(0, 0);

        assertTrue(game.getMinefield().getCell(0,0).isRevealed());
        assertEquals(1, game.getMinefield().getRevealed());
    }

    @Test
    void testRevealAlreadyRevealedCell() {
        // First reveal
        game.revealCell(0, 0);
        int initialRevealedCount = game.getMinefield().getRevealed();

        // Try to reveal the same cell again
        game.revealCell(0, 0);
        assertTrue(game.getMinefield().getCell(0, 0).isRevealed());
        assertEquals(initialRevealedCount, game.getMinefield().getRevealed());
    }

    @Test
    void testFlagCell() {
        assertEquals(0, game.getFlagsPlaced());

        game.flagCell(0, 0);
        assertTrue(game.getMinefield().getCell(0, 0).isFlagged());
        assertEquals(1, game.getFlagsPlaced());

        game.flagCell(0, 0);
        assertFalse(game.getMinefield().getCell(0, 0).isFlagged());
        assertEquals(0, game.getFlagsPlaced());

        game.revealCell(0, 0);
        game.flagCell(0, 0);
        assertFalse(game.getMinefield().getCell(0, 0).isFlagged());
        assertEquals(0, game.getFlagsPlaced());
    }

    @Test
    void testGetMinesLeft() {
        assertEquals(mines, game.getMinesLeft()); // Initially, mines left should equal total mines

        game.flagCell(0, 0); // Flag a cell
        assertEquals(mines - 1, game.getMinesLeft());

        game.flagCell(0, 1); // Flag another cell
        assertEquals(mines - 2, game.getMinesLeft());

        game.flagCell(0, 0); // Unflag the first cell
        assertEquals(mines - 1, game.getMinesLeft());
    }
}



