package game;

import ms.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations;


public class GameInitializationTests {

    private Game game;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGameInitialization() {

        game = new Game(10, 10, 10);
        MineField voidMinefield = new MineField(10, 10, 0);

        assertEquals(voidMinefield, game.getMinefield());
        assertEquals(10, game.getMinesLeft());
        assertFalse(game.getGameOver());
        assertEquals(0, game.getRevealed());
        assertEquals(100, game.getUnrevealedCount());
    }

    @Test
    void testGameInitializationWithEasyParameters() {

        game = new Game(9, 9, 10);
        game.revealCell(new Position(0,0));

        assertEquals(9, game.getMinefield().getHeight());
        assertEquals(9, game.getMinefield().getWidth());
        assertEquals(10, game.getMinefield().getMines());
    }

    @Test
    void testGameInitializationWithMediumParameters() {

        game = new Game(16, 16, 40);
        game.revealCell(new Position(0,0));

        assertEquals(16, game.getMinefield().getHeight());
        assertEquals(16, game.getMinefield().getWidth());
        assertEquals(40, game.getMinefield().getMines());
    }

    @Test
    void testGameInitializationWithHardParameters() {

        game = new Game(16, 30, 99);
        game.revealCell(new Position(0, 0));

        assertEquals(16, game.getMinefield().getHeight());
        assertEquals(30, game.getMinefield().getWidth());
        assertEquals(99, game.getMinefield().getMines());
    }

    @Test
    void testMinefieldInitializationOnFirstClick() {

        game = new Game(10, 10, 10);
        game.revealCell(new Position(0, 0));

        assertEquals(10, game.getMinefield().getHeight());
        assertEquals(10, game.getMinefield().getWidth());
        assertEquals(10, game.getMinefield().getMines());
    }
}
