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

        game = new Game(Difficulty.EASY);
        MineField voidMinefield = new MineField(9, 9, 0);

        assertEquals(voidMinefield, game.getMinefield());
        assertEquals(10, game.getMinesLeft());
        assertFalse(game.getGameOver());
        assertEquals(0, game.getRevealed());
        assertEquals(81, game.getUnrevealedCount());
    }

    @Test
    void testGameInitializationWithEasyParameters() {

        game = new Game(Difficulty.EASY);
        game.revealCell(new Position(0,0));

        assertEquals(9, game.getMinefield().getHeight());
        assertEquals(9, game.getMinefield().getWidth());
        assertEquals(10, game.getMinefield().getMines());
    }

    @Test
    void testGameInitializationWithMediumParameters() {

        game = new Game(Difficulty.MEDIUM);
        game.revealCell(new Position(0,0));

        assertEquals(16, game.getMinefield().getHeight());
        assertEquals(16, game.getMinefield().getWidth());
        assertEquals(40, game.getMinefield().getMines());
    }

    @Test
    void testGameInitializationWithHardParameters() {

        game = new Game(Difficulty.HARD);
        game.revealCell(new Position(0, 0));

        assertEquals(16, game.getMinefield().getHeight());
        assertEquals(30, game.getMinefield().getWidth());
        assertEquals(99, game.getMinefield().getMines());
    }

    @Test
    void testMinefieldInitializationOnFirstClick() {

        game = new Game(Difficulty.EASY);
        game.revealCell(new Position(0, 0));

        assertEquals(9, game.getMinefield().getHeight());
        assertEquals(9, game.getMinefield().getWidth());
        assertEquals(10, game.getMinefield().getMines());
    }
}
