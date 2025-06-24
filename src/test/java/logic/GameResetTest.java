package logic;

import ms.logic.Game;
import ms.logic.MineFieldFactory;
import ms.model.GridDimension;
import ms.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class GameResetTest {

    private static final GridDimension DIMENSIONS = new GridDimension(3, 3);
    private static final int MINE_COUNT = 1;
    private static final Position SAFE_POSITION = new Position(0, 0);
    private static final Position MINE_POSITION = new Position(0, 1);
    private static final Position FLAG_POSITION = new Position(1, 1);

    private Game game;

    @Mock
    private MineFieldFactory mockMineFieldFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        LogicUtils.setupMockFactoryForReset(mockMineFieldFactory, DIMENSIONS, MINE_COUNT);
    }

    @Test
    void testResetGameClearsGameOverStatus() throws InterruptedException {

        game = LogicUtils.createAndSetupGameForReset(
                DIMENSIONS, MINE_COUNT, mockMineFieldFactory,
                SAFE_POSITION, MINE_POSITION, FLAG_POSITION);

        assertTrue(game.isGameOver(), "Game should be over before reset");

        game.resetGame();

        assertFalse(game.isGameOver(), "Game should not be over after reset");
    }

    @Test
    void testResetGameClearsTimer() throws InterruptedException {

        game = LogicUtils.createAndSetupGameForReset(
                DIMENSIONS, MINE_COUNT, mockMineFieldFactory,
                SAFE_POSITION, MINE_POSITION, FLAG_POSITION);

        assertTrue(game.getElapsedTime() > 0, "Timer should have started");

        game.resetGame();

        assertEquals(0, game.getElapsedTime(), "Timer should be reset");
    }

    @Test
    void testResetGameClearsFlagsPlaced() throws InterruptedException {

        game = LogicUtils.createAndSetupGameForReset(
                DIMENSIONS, MINE_COUNT, mockMineFieldFactory,
                SAFE_POSITION, MINE_POSITION, FLAG_POSITION);

        assertEquals(1, game.getFlagsPlaced(), "Flags should have been placed");

        game.resetGame();

        assertEquals(0, game.getFlagsPlaced(), "Flags placed should be reset");
    }

    @Test
    void testResetGameRestoresMinesLeft() throws InterruptedException {

        game = LogicUtils.createAndSetupGameForReset(
                DIMENSIONS, MINE_COUNT, mockMineFieldFactory,
                SAFE_POSITION, MINE_POSITION, FLAG_POSITION);

        game.resetGame();

        assertEquals(1, game.getMinesLeft(), "Mines left should be reset");
    }

    @Test
    void testResetGameRestoresInitialCellStates() throws InterruptedException {

        game = LogicUtils.createAndSetupGameForReset(
                DIMENSIONS, MINE_COUNT, mockMineFieldFactory,
                SAFE_POSITION, MINE_POSITION, FLAG_POSITION);

        assertTrue(game.getMinefield().getCell(new Position(0, 0)).isRevealed(), "A cell should be revealed");

        game.resetGame();

        assertTrue(LogicUtils.verifyInitialCellStates(game), "All cells should be in initial state after reset");
    }
}

