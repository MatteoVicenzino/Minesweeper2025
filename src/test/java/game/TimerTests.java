package game;

import ms.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class TimerTests {

    private Game game;
    private final int mines = 10;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        game = new Game(Difficulty.EASY);
    }

    @Test
    void testTimerStartsOnFirstReveal() throws InterruptedException {
        assertEquals(0, game.getElapsedTime(), "Initial elapsed time should be 0");

        game.revealCell(new Position(0, 0));
        for (int row = 0; row < game.getMinefield().getHeight(); row++) {
            for (int col = 0; col < game.getMinefield().getWidth(); col++) {
                if (!game.getMinefield().getCell(new Position(row, col)).isMined()) {
                    game.revealCell(new Position(row, col));
                    Thread.sleep(10);
                    assertTrue(game.getElapsedTime() > 0, "Timer should start after first reveal");
                    return;
                }
            }
        }
        fail("Could not find a non-mine cell to reveal for the test.");
    }

    @Test
    void testTimerStopsOnGameLose() throws InterruptedException {
        boolean[][] minePattern = {
                {false, true, false},
                {false, false, false},
                {false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(3, 3, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(3, 3, 1)).thenReturn(testMineField);

        game.revealCell(new Position(0, 1));

        assertTrue(game.getGameOver(), "Game should be over after revealing a mine");
        long endTime = game.getElapsedTime();
        Thread.sleep(10);
        assertEquals(endTime, game.getElapsedTime(), "Timer should stop on game over (lose)");
    }

    @Test
    void testTimerStopsOnGameWin() throws InterruptedException {
        game.revealCell(new Position(0, 0));
        GameTestsHelper.revealAllNonMineCells(game);
        assertTrue(game.getGameOver(), "Game should be over after revealing all non-mine cells");
        long endTime = game.getElapsedTime();
        Thread.sleep(10);
        assertEquals(endTime, game.getElapsedTime(), "Timer should stop on game over (win)");
    }
}
