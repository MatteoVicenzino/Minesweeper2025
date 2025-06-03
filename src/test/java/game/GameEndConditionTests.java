package game;

import ms.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class GameEndConditionTests {

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
    void testRevealMineEndsGame() {
        boolean[][] minePattern = GameTestsHelper.createSimpleCenterMinePattern();
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(3, 3, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(3, 3, 1)).thenReturn(testMineField);

        game.revealCell(new Position(1, 1));
        assertTrue(game.getGameOver(), "Game should end when a mine is revealed");
    }

    @Test
    void testGameOverWhenAllCellsRevealed() {
        game.revealCell(new Position(0, 0));
        GameTestsHelper.revealAllNonMineCells(game);
        assertTrue(game.getGameOver(), "Game should end when all non-mine cells are revealed");
    }
}