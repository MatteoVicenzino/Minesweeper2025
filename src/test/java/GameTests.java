import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    void testGameInitialization() {
        assertNull(game.getMinefield()); // Ensure minefield is null before generation
        assertNotNull(game.initMinefield());
        assertEquals(10, game.getMinesLeft());
        assertFalse(game.getGameOver());
    }
}
