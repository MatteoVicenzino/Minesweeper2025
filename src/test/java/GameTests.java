import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        assertNotNull(game.initMinefield()); // Ensure minefield is initialized
        assertEquals(10, game.getMinesLeft()); // Default mines left
        assertFalse(game.getGameOver()); // Ensure game is not over on start
    }

    @Test
    void testMinefieldInitializationOnGameCreation() {
        assertNotNull(game.getMinefield());
    }
}



