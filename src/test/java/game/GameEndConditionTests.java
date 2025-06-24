package game;

import ms.logic.Game;
import ms.logic.MineFieldFactory;
import ms.model.Difficulty;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class GameEndConditionTests {

    private Game game;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        game = new Game(Difficulty.EASY);
    }

    @Test
    void testRevealMineEndsGame() {

        GridDimension dimensions = new GridDimension(3, 3);

        boolean[][] minePattern = GameTestsHelper.createSimpleCenterMinePattern();
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(dimensions, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(dimensions, 1)).thenReturn(testMineField);

        game.revealCell(new Position(1, 1));
        assertTrue(game.isGameOver(), "Game should end when a mine is revealed");
    }

    @Test
    void testGameWonWhenAllNonMineCellsRevealed() {
        game.revealCell(new Position(0, 0));
        GameTestsHelper.revealAllNonMineCells(game);
        assertTrue(game.isGameOver(), "Game should end when all non-mine cells are revealed");
    }
}