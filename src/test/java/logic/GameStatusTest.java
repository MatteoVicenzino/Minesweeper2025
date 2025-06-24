package logic;

import ms.logic.Game;
import ms.logic.MineFieldFactory;
import ms.logic.status.GameStatus;
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

public class GameStatusTest {

    private Game game;
    private GridDimension dimensions;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        game = new Game(Difficulty.EASY);
        dimensions = new GridDimension(3, 3);
    }

    @Test
    void testNewGameHasNotStartedStatus() {
        assertEquals(GameStatus.NOT_STARTED, game.getGameStatus());
        assertFalse(game.isGameOver(), "Game should not be over initially");
    }

    @Test
    void testGameStatusRemainsInProgressDuringNormalPlay() {
        game.revealCell(new Position(0, 0));
        assertEquals(GameStatus.IN_PROGRESS, game.getGameStatus());
        assertFalse(game.isGameOver(), "Game should remain in progress during normal play");
    }

    @Test
    void testGameStatusBecomesLostWhenMineRevealed() {

        boolean[][] minePattern = LogicUtils.createSimpleCenterMinePattern();
        MineField testMineField = LogicUtils.createMineFieldWithPattern(minePattern);
        game = LogicUtils.createGameWithMockFactory(dimensions, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(dimensions, 1)).thenReturn(testMineField);

        game.revealCell(new Position(1, 1)); // This should reveal the mine

        assertEquals(GameStatus.LOST, game.getGameStatus());
        assertTrue(game.isGameOver(), "Game should be over when mine is revealed");
    }

    @Test
    void testGameStatusBecomesWonWhenAllNonMineCellsRevealed() {
        game.revealCell(new Position(0, 0)); // Initialize the minefield
        LogicUtils.revealAllNonMineCells(game);

        assertEquals(GameStatus.WON, game.getGameStatus());
        assertTrue(game.isGameOver(), "Game should be over when all non-mine cells are revealed");
    }

    @Test
    void testResetGameResetsStatusToNotStarted() {

        boolean[][] minePattern = LogicUtils.createSimpleCenterMinePattern();
        MineField mineFieldWithPattern = LogicUtils.createMineFieldWithPattern(minePattern);

        when(mockMineFieldFactory.createMineField(dimensions, 1)).thenReturn(mineFieldWithPattern);
        game = LogicUtils.createGameWithMockFactory(dimensions, 1, mockMineFieldFactory);
        game.revealCell(new Position(1, 1));
        assertEquals(GameStatus.LOST, game.getGameStatus());

        game.resetGame();

        assertEquals(GameStatus.NOT_STARTED, game.getGameStatus());
        assertFalse(game.isGameOver(), "Game should not be over after reset");
    }

    @Test
    void testCannotRevealCellsWhenGameIsLost() {

        boolean[][] minePattern = LogicUtils.createSimpleCenterMinePattern();
        MineField testMineField = LogicUtils.createMineFieldWithPattern(minePattern);
        game = LogicUtils.createGameWithMockFactory(dimensions, 1, mockMineFieldFactory);
        when(mockMineFieldFactory.createMineField(dimensions, 1)).thenReturn(testMineField);

        game.revealCell(new Position(1, 1)); // Lose the game
        assertEquals(GameStatus.LOST, game.getGameStatus());

        int revealedCountBeforeAttempt = game.getRevealed();

        assertThrows(Game.InvalidGameOperationException.class, () ->
                game.revealCell(new Position(0, 0)), "Should throw exception when trying to reveal cell after game is lost");

        assertEquals(revealedCountBeforeAttempt, game.getRevealed(),
                "No additional cells should be revealed when game is lost");
    }

    @Test
    void testCannotRevealCellsWhenGameIsWon() {
        game.revealCell(new Position(0, 0)); // Initialize the minefield
        LogicUtils.revealAllNonMineCells(game);
        assertEquals(GameStatus.WON, game.getGameStatus());

        int revealedCountBeforeAttempt = game.getRevealed();
        assertThrows(Game.InvalidGameOperationException.class, () ->
                game.revealCell(new Position(0, 0)), "Should throw exception when trying to reveal cell after game is won");

        assertEquals(revealedCountBeforeAttempt, game.getRevealed(),
                "No additional cells should be revealed when game is won");
    }
}