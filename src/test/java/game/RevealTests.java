package game;

import ms.Game;
import ms.MineField;
import ms.MineFieldFactory;
import ms.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RevealTests {

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
    void testRevealCell() {
        boolean[][] minePattern = {
                {false, false, false, false},
                {false, false, false, false},
                {false, false, true, false},
                {false, false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(4, 4, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(4, 4, 1)).thenReturn(testMineField);

        game.revealCell(new Position(3, 3));

        assertTrue(game.getMinefield().getCell(new Position(3,3)).isRevealed());
        assertFalse(game.getMinefield().getCell(new Position(0,0)).isRevealed());
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
    void testRevealEmptyCellsCascade() throws InterruptedException {
        boolean[][] minePattern = GameTestsHelper.createCascadeTestPattern();
        MineField realMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        MineField spyMineField = spy(realMineField);

        doNothing().when(spyMineField).initializeGrid(any(Position.class));

        when(mockMineFieldFactory.createMineField(5, 5, 0)).thenReturn(new MineField(5, 5, 0));
        when(mockMineFieldFactory.createMineField(5, 5, 8)).thenReturn(spyMineField);

        game = GameTestsHelper.createGameWithMockFactory(5, 5, 8, mockMineFieldFactory);

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
        boolean[][] minePattern = {
                {false, false, false},
                {false, true, false},
                {false, false, false}
        };
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(3, 3, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(3, 3, 1)).thenReturn(testMineField);

        game.revealCell(new Position(0, 0));
        game.flagCell(new Position(0, 1));

        int revealedCountBefore = game.getRevealed();
        game.revealCell(new Position(0, 1));

        assertFalse(game.getMinefield().getCell(new Position(0, 1)).isRevealed(),
                "Flagged cell should not be revealed");
        assertEquals(revealedCountBefore, game.getRevealed(),
                "Revealed count should not change when trying to reveal flagged cell");
    }
}
