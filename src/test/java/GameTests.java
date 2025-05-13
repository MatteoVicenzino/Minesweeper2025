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
    void testInitialization() {
        assertNotNull(game.getMinefield()); // Ensure minefield is initialized
        assertEquals(10, game.getMinesLeft()); // Default mines left
        assertFalse(game.getGameOver()); // Ensure game is not over on start
    }
}



