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
        assertNull(game.getMinefield()); // Ensure minefield is null before generation
        assertNotNull(game.initMinefield());
        assertEquals(10, game.getMinesLeft());
        assertFalse(game.getGameOver());
    }
}
