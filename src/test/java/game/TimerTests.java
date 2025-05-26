package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.MockitoAnnotations;
import ms.Game;

public class TimerTests {

    private Game game;
    private final int mines = 10;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        game = new Game(10, 10, mines);
    }

    @Test
    void testTimerStartsOnFirstReveal() throws InterruptedException {
        assertEquals(0, game.getElapsedTime(), "Initial elapsed time should be 0");

        game.revealCell(0, 0);
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                if (!game.getMinefield().getCell(row, col).isMined()) {
                    game.revealCell(row, col);
                    Thread.sleep(10);
                    assertTrue(game.getElapsedTime() > 0, "Timer should start after first reveal");
                    return;
                }
            }
        }
        fail("Could not find a non-mine cell to reveal for the test.");
    }
}
