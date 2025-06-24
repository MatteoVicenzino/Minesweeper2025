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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FlagTests {

    private Game game;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        Difficulty difficulty = Difficulty.EASY;
        game = new Game(difficulty);
    }

    @Test
    void testFlagCellTogglesStateAndUpdatesFlagCount() {

        GridDimension dimensions = new GridDimension(5, 5);

        boolean[][] minePattern = {
                {false, true, false, false, false},
                {true, false, false, false, false},
                {false, false, true, false, false},
                {false, false, false, true, false},
                {false, false, false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(dimensions, 4, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(dimensions, 4)).thenReturn(testMineField);

        game.revealCell(new Position(4, 4));

        assertEquals(0, game.getFlagsPlaced());

        game.flagCell(new Position(1, 1));
        assertTrue(game.getMinefield().getCell(new Position(1, 1)).isFlagged());
        assertEquals(1, game.getFlagsPlaced());

        game.flagCell(new Position(1, 1));
        assertFalse(game.getMinefield().getCell(new Position(1, 1)).isFlagged());
        assertEquals(0, game.getFlagsPlaced());

        game.revealCell(new Position(3, 4));
        assertThrows(Game.InvalidGameOperationException.class,
                () -> game.flagCell(new Position(3, 4)),
                "Should throw exception when trying to flag a revealed cell");
        assertFalse(game.getMinefield().getCell(new Position(3, 4)).isFlagged());
        assertEquals(0, game.getFlagsPlaced());
    }

    @Test
    void testFlagCellUpdatesMinesLeftCounter() {

        GridDimension dimensions = new GridDimension(4, 4);

        boolean[][] minePattern = {
                {false, false, false, false},
                {false, true, false, false},
                {false, false, true, false},
                {false, false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(dimensions, 2, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(dimensions, 2)).thenReturn(testMineField);

        game.revealCell(new Position(0, 0));

        assertEquals(2, game.getMinesLeft());

        game.flagCell(new Position(1, 1));
        assertEquals(1, game.getMinesLeft());

        game.flagCell(new Position(0, 1));
        assertEquals(0, game.getMinesLeft());

        game.flagCell(new Position(1, 1));
        assertEquals(1, game.getMinesLeft());
    }
}
