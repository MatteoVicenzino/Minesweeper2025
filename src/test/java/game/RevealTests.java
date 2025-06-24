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
import static org.mockito.Mockito.*;

public class RevealTests {

    private Game game;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        game = new Game(Difficulty.EASY);
    }

    @Test
    void testRevealCell() {

        GridDimension dimensions = new GridDimension(4, 4);

        boolean[][] minePattern = {
                {false, false, false, false},
                {false, false, false, false},
                {false, false, true, false},
                {false, false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(dimensions, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(dimensions, 1)).thenReturn(testMineField);

        game.revealCell(new Position(3, 3));

        assertTrue(game.getMinefield().getCell(new Position(3, 3)).isRevealed());
        assertFalse(game.getMinefield().getCell(new Position(0, 0)).isRevealed());
        assertEquals(1, game.getRevealed());
    }

    @Test
    void testRevealAlreadyRevealedCell() {
        game.revealCell(new Position(0, 0));
        int initialRevealedCount = game.getRevealed();

        game.revealCell(new Position(0, 0));
        assertTrue(game.getMinefield().getCell(new Position(0, 0)).isRevealed());
        assertEquals(initialRevealedCount, game.getRevealed());
    }

    @Test
    void testRevealEmptyCellsCascade() {

        GridDimension dimensions = new GridDimension(5, 5);

        boolean[][] minePattern = GameTestsHelper.createCascadeTestPattern();
        MineField realMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        MineField spyMineField = spy(realMineField);

        doNothing().when(spyMineField).initializeGrid(any(Position.class));

        when(mockMineFieldFactory.createMineField(dimensions, 0)).thenReturn(new MineField(dimensions, 0));
        when(mockMineFieldFactory.createMineField(dimensions, 8)).thenReturn(spyMineField);

        game = GameTestsHelper.createGameWithMockFactory(dimensions, 8, mockMineFieldFactory);

        game.revealCell(new Position(0, 0));

        assertFalse(game.getMinefield().getCell(new Position(1, 0)).isRevealed(), "Cell (1, 0) should not be revealed");
        assertFalse(game.getMinefield().getCell(new Position(0, 1)).isRevealed(), "Cell (0, 1) should not be revealed");
        assertFalse(game.getMinefield().getCell(new Position(1, 1)).isRevealed(), "Cell (1, 1) should not be revealed");

        game.revealCell(new Position(0, 4));
        assertTrue(game.getMinefield().getCell(new Position(0, 3)).isRevealed(), "Cell (0, 3) should be revealed");
        assertTrue(game.getMinefield().getCell(new Position(1, 3)).isRevealed(), "Cell (1, 3) should be revealed");
        assertTrue(game.getMinefield().getCell(new Position(1, 4)).isRevealed(), "Cell (1, 4) should be revealed");

        game.revealCell(new Position(4, 4));
        assertFalse(game.getMinefield().getCell(new Position(3, 4)).isRevealed(), "Cell (3, 4) should not be revealed");
        assertFalse(game.getMinefield().getCell(new Position(3, 3)).isRevealed(), "Cell (3, 3) should not be revealed");
        assertFalse(game.getMinefield().getCell(new Position(4, 3)).isRevealed(), "Cell (4, 3) should not be revealed");

        game.revealCell(new Position(4, 0));
        assertTrue(game.getMinefield().getCell(new Position(4, 1)).isRevealed(), "Cell (4, 1) should be revealed");
        assertTrue(game.getMinefield().getCell(new Position(4, 2)).isRevealed(), "Cell (4, 2) should be revealed");
        assertFalse(game.getMinefield().getCell(new Position(4, 3)).isRevealed(), "Cell (4, 3) should not be revealed");

        assertTrue(game.getMinefield().getCell(new Position(3, 0)).isRevealed(), "Cell (3, 0) should be revealed");
        assertTrue(game.getMinefield().getCell(new Position(3, 1)).isRevealed(), "Cell (3, 1) should be revealed");
        assertTrue(game.getMinefield().getCell(new Position(3, 2)).isRevealed(), "Cell (3, 2) should be revealed");
        assertFalse(game.getMinefield().getCell(new Position(3, 3)).isRevealed(), "Cell (3, 3) should not be revealed");

        assertTrue(game.getMinefield().getCell(new Position(2, 0)).isRevealed(), "Cell (2, 0) should be revealed");
        assertTrue(game.getMinefield().getCell(new Position(2, 1)).isRevealed(), "Cell (2, 1) should be revealed");
        assertFalse(game.getMinefield().getCell(new Position(2, 2)).isRevealed(), "Cell (2, 2) should not be revealed");

        assertFalse(game.getMinefield().getCell(new Position(1, 0)).isRevealed(), "Cell (1, 0) should not be revealed");
    }

    @Test
    void testRevealFlaggedCell() {

        GridDimension dimensions = new GridDimension(3, 3);

        boolean[][] minePattern = {
                {false, false, false},
                {false, true, false},
                {false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(dimensions, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(dimensions, 1)).thenReturn(testMineField);

        game.revealCell(new Position(0, 0));
        game.flagCell(new Position(0, 1));

        int revealedCountBefore = game.getRevealed();

        assertThrows(Game.InvalidGameOperationException.class,
                () -> game.revealCell(new Position(0, 1)),
                "Should throw exception when trying to reveal a flagged cell");

        assertTrue(game.getMinefield().getCell(new Position(0, 1)).isFlagged(),
                "Cell should remain flagged after exception");

        assertFalse(game.getMinefield().getCell(new Position(0, 1)).isRevealed(),
                "Flagged cell should not be revealed");
        assertEquals(revealedCountBefore, game.getRevealed(),
                "Revealed count should not change when trying to reveal flagged cell");
    }
}
