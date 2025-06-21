package game;

import ms.model.Difficulty;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;
import ms.logic.Game;
import ms.logic.GameStatus;
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

        Difficulty difficulty = Difficulty.EASY;
        game = new Game(difficulty);
        MineField voidMinefield = new MineField(GridDimension.fromDifficulty(difficulty), 0);

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

    @Test
    void testInvalidFirstRevealDoesNotInitializeMinefield() {

        Difficulty difficulty = Difficulty.EASY;
        GridDimension dimensions = GridDimension.fromDifficulty(difficulty);
        game = new Game(difficulty);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            game.revealCell(new Position(10, 10));
        });

        assertEquals(GameStatus.NOT_STARTED, game.getGameStatus());
        assertEquals(0, game.getRevealed());
        assertEquals(0, game.getElapsedTime());

        MineField voidMinefield = new MineField(dimensions, 0);
        assertEquals(voidMinefield, game.getMinefield());
    }
}
