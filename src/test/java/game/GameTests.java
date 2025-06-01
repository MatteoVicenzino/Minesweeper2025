package game;

import ms.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import ms.Game;
import ms.MineField;
import ms.MineFieldFactory;

public class GameTests {

    private Game game;
    private final int mines = 10;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        game = new Game(10, 10, mines);
    }

    @Test
    void testFlagCell() {
        boolean[][] minePattern = {
                {false, true, false, false, false},
                {true, false, false, false, false},
                {false, false, true, false, false},
                {false, false, false, true, false},
                {false, false, false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(5, 5, 4, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(5, 5, 4)).thenReturn(testMineField);

        game.revealCell(new Position(4, 4));

        assertEquals(0, game.getFlagsPlaced());

        game.flagCell(new Position(1, 1));
        assertTrue(game.getMinefield().getCell(new Position(1, 1)).isFlagged());
        assertEquals(1, game.getFlagsPlaced());

        game.flagCell(new Position(1, 1));
        assertFalse(game.getMinefield().getCell(new Position(1, 1)).isFlagged());
        assertEquals(0, game.getFlagsPlaced());

        game.revealCell(new Position(3, 4));
        game.flagCell(new Position(3, 4));
        assertFalse(game.getMinefield().getCell(new Position(3, 4)).isFlagged());
        assertEquals(0, game.getFlagsPlaced());
    }

    @Test
    void testGetMinesLeft() {
        boolean[][] minePattern = {
                {false, false, false, false},
                {false, true, false, false},
                {false, false, true, false},
                {false, false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(4, 4, 2, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(4, 4, 2)).thenReturn(testMineField);

        game.revealCell(new Position(0, 0));

        assertEquals(2, game.getMinesLeft());

        game.flagCell(new Position(1, 1));
        assertEquals(1, game.getMinesLeft());

        game.flagCell(new Position(0, 1));
        assertEquals(0, game.getMinesLeft());

        game.flagCell(new Position(1, 1));
        assertEquals(1, game.getMinesLeft());
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



