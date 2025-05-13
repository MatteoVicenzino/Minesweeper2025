import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ms.Game;

public class GameTests {

    private Game game;

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
    void testRevealFreeCell() {
        game.revealCell(0, 0);

        assertTrue(game.getMinefield().getCell(0,0).isRevealed());
        assertEquals(1, game.getMinefield().getRevealed());
    }
}



