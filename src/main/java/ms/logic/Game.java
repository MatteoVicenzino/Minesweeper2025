package ms.logic;

import ms.logic.operation.FlagOperation;
import ms.logic.operation.GameOperation;
import ms.logic.operation.RevealOperation;
import ms.logic.rules.FlagRules;
import ms.logic.rules.GameRules;
import ms.logic.rules.RevealRules;
import ms.logic.status.GameStatistics;
import ms.logic.status.GameStatus;
import ms.logic.status.GameStatusManager;
import ms.logic.status.Timer;
import ms.model.Difficulty;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;

/**
 * The {@code Game} class represents a Minesweeper game instance.
 * It manages the game state, including the minefield, game status, and statistics.
 * It provides methods to reveal cells, flag cells, and reset the game.
 */
public class Game {

    private final GridDimension dimensions;
    private final int totalMines;
    private final GameStatusManager statusManager;
    private final MineFieldFactory mineFieldFactory;
    private final Timer timer;
    private final GameStatistics stats;
    private MineField minefield;
    private boolean isFirstReveal;

    /**
     * Constructs a new Game instance with specified dimensions, total mines, and a mine field factory.
     *
     * @param dimensions the dimensions of the game grid
     * @param totalMines the total number of mines in the game
     * @param mineFieldFactory the factory to create mine fields
     */
    public Game(GridDimension dimensions, int totalMines, MineFieldFactory mineFieldFactory) {
        this.dimensions = dimensions;
        this.totalMines = totalMines;
        this.mineFieldFactory = mineFieldFactory;
        this.minefield = mineFieldFactory.createMineField(dimensions, 0);
        this.isFirstReveal = true;
        this.timer = new Timer();
        this.stats = new GameStatistics(dimensions.height(), dimensions.width(), totalMines);
        this.statusManager = new GameStatusManager();
    }

    /**
     * Constructs a new Game instance with specified difficulty.
     * The dimensions and total mines are derived from the difficulty level.
     *
     * @param difficulty the difficulty level of the game
     */
    public Game(Difficulty difficulty) {
        this(GridDimension.fromDifficulty(difficulty), difficulty.getMines(), new DefaultMineFieldFactory());
    }

    /**
     * Gets the current minefield of the game.
     *
     * @return the minefield
     */
    public MineField getMinefield() {
        return minefield;
    }

    /**
     * Gets the dimensions of the game grid.
     *
     * @return the dimensions of the grid
     */
    public int getRevealed() {
        return stats.getRevealedCount();
    }

    /**
     * Gets the number of flags placed in the game.
     *
     * @return the number of flags placed
     */
    public int getFlagsPlaced() {
        return stats.getFlagsPlaced();
    }

    /**
     * Gets the number of mines left in the game.
     *
     * @return the number of mines left
     */
    public int getMinesLeft() {
        return stats.getMinesLeft();
    }

    /**
     * Gets the current game status.
     *
     * @return the current game status
     */
    public GameStatus getGameStatus() {
        return statusManager.getCurrentStatus();
    }

    /**
     * Checks if the game is over (either won or lost).
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return statusManager.isGameOver();
    }

    /**
     * Gets the number of unrevealed cells in the game.
     *
     * @return the count of unrevealed cells
     */
    public int getUnrevealedCount() {
        return stats.getUnrevealedCount();
    }

    /**
     * Gets the total number of mines in the game.
     *
     * @return the total number of mines
     */
    public int getTotalMines() {
        return totalMines;
    }

    /**
     * Gets the total number of non-mine cells in the game.
     *
     * @return the total number of non-mine cells
     */
    public int getTotalOfNonMineCells() {
        return stats.getTotalOfNonMineCells();
    }

    /**
     * Gets the elapsed time since the game started.
     *
     * @return the elapsed time in milliseconds
     */
    public long getElapsedTime() {
        return timer.getElapsedTime();
    }

    /**
     * Reveals a cell at the specified position.
     * If this is the first reveal, it initializes the minefield and starts the timer.
     *
     * @param position the position of the cell to reveal
     * @throws InvalidGameOperationException if the game is already over or if the position is invalid
     */
    public void revealCell(Position position) {
        if (isFirstReveal) {
            handleFirstReveal(position);
        }

        GameRules rules = new RevealRules(dimensions, minefield, statusManager);
        rules.validate(position);
        GameOperation revealOperation = new RevealOperation(minefield, dimensions, stats, statusManager, timer);
        revealOperation.execute(position);
    }

    /**
     * Flags or unflags a cell at the specified position.
     * If this is the first reveal, it throws an exception since flagging is not allowed.
     *
     * @param position the position of the cell to flag
     * @throws InvalidGameOperationException if the game is already over, if the position is invalid,
     *         or if flagging is attempted on the first move
     */
    public void flagCell(Position position) {
        GameRules rules = new FlagRules(dimensions, minefield, isFirstReveal);
        rules.validate(position);
        GameOperation flagOperation = new FlagOperation(minefield, stats);
        flagOperation.execute(position);
    }

    /**
     * Handles the first reveal of the game.
     * Initializes the minefield, starts the game status, and starts the timer.
     *
     * @param position the position of the first cell to reveal
     * @throws InvalidGameOperationException if the position is invalid
     */
    private void handleFirstReveal(Position position) {
        dimensions.validatePosition(position);
        statusManager.startGame();
        this.minefield = mineFieldFactory.createMineField(dimensions, totalMines);
        this.minefield.initializeGrid(position);
        timer.start();
        isFirstReveal = false;
    }

    /**
     * Resets the game to its initial state.
     * Reinitializes the minefield, resets statistics, status manager, and timer.
     */
    public void resetGame() {
        this.minefield = mineFieldFactory.createMineField(dimensions, totalMines);
        stats.reset();
        statusManager.resetGame();
        timer.reset();
        this.isFirstReveal = true;
    }

    /**
     * Exception thrown when an invalid game operation is attempted.
     */
    public static class InvalidGameOperationException extends IllegalStateException {
        public InvalidGameOperationException(String message) {
            super(message);
        }
    }
}