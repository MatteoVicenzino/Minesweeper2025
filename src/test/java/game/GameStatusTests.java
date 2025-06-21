package game;

import ms.model.Difficulty;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;
import ms.logic.Game;
import ms.logic.GameStatus;
import ms.logic.MineFieldFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class GameStatusTests {

    private Game game;
    private GridDimension dimensions;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        game = new Game(Difficulty.EASY);
        dimensions = new GridDimension(3,3);
    }

    @Test
    void testGameInitialStatusIsInProgress() {
        assertEquals(GameStatus.NOT_STARTED, game.getGameStatus());
        assertFalse(game.getGameOver(), "Game should not be over initially");
    }

    @Test
    void testGameStatusRemainsInProgressDuringNormalPlay() {
        game.revealCell(new Position(0, 0));
        assertEquals(GameStatus.IN_PROGRESS, game.getGameStatus());
        assertFalse(game.getGameOver(), "Game should remain in progress during normal play");
    }

    @Test
    void testGameStatusBecomesLostWhenMineRevealed() {

        boolean[][] minePattern = GameTestsHelper.createSimpleCenterMinePattern();
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(dimensions, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(dimensions, 1)).thenReturn(testMineField);

        game.revealCell(new Position(1, 1)); // This should reveal the mine

        assertEquals(GameStatus.LOST, game.getGameStatus());
        assertTrue(game.getGameOver(), "Game should be over when mine is revealed");
    }

    @Test
    void testGameStatusBecomesWonWhenAllNonMineCellsRevealed() {
        game.revealCell(new Position(0, 0)); // Initialize the minefield
        GameTestsHelper.revealAllNonMineCells(game);

        assertEquals(GameStatus.WON, game.getGameStatus());
        assertTrue(game.getGameOver(), "Game should be over when all non-mine cells are revealed");
    }

    @Test
    void testGameStatusOnReset() {

        boolean[][] minePattern = GameTestsHelper.createSimpleCenterMinePattern();
        MineField mineFieldWithPattern = GameTestsHelper.createMineFieldWithPattern(minePattern);

        when(mockMineFieldFactory.createMineField(dimensions, 1)).thenReturn(mineFieldWithPattern);
        game = GameTestsHelper.createGameWithMockFactory(dimensions, 1, mockMineFieldFactory);
        game.revealCell(new Position(1, 1));
        assertEquals(GameStatus.LOST, game.getGameStatus());

        game.resetGame();

        assertEquals(GameStatus.NOT_STARTED, game.getGameStatus());
        assertFalse(game.getGameOver(), "Game should not be over after reset");
    }

    @Test
    void testCannotRevealCellsWhenGameIsLost() {

        boolean[][] minePattern = GameTestsHelper.createSimpleCenterMinePattern();
        MineField testMineField = GameTestsHelper.createMineFieldWithPattern(minePattern);
        game = GameTestsHelper.createGameWithMockFactory(dimensions, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(dimensions, 1)).thenReturn(testMineField);

        game.revealCell(new Position(1, 1)); // Lose the game
        assertEquals(GameStatus.LOST, game.getGameStatus());

        int revealedCountBeforeAttempt = game.getRevealed();
        game.revealCell(new Position(0, 0)); // Try to reveal another cell

        assertEquals(revealedCountBeforeAttempt, game.getRevealed(),
                "No additional cells should be revealed when game is lost");
    }

    @Test
    void testCannotRevealCellsWhenGameIsWon() {
        game.revealCell(new Position(0, 0)); // Initialize the minefield
        GameTestsHelper.revealAllNonMineCells(game);
        assertEquals(GameStatus.WON, game.getGameStatus());

        int revealedCountBeforeAttempt = game.getRevealed();
        game.revealCell(new Position(0, 0)); // Try to reveal a cell again

        assertEquals(revealedCountBeforeAttempt, game.getRevealed(),
                "No additional cells should be revealed when game is won");
    }
}