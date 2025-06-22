package ms.logic;

import ms.model.*;

public class Game {

    private final GridDimension dimensions;
    private final int totalMines;
    private final GameStatusManager statusManager;
    private final MineFieldFactory mineFieldFactory;
    private final Timer timer;
    private final GameStatistics stats;
    private MineField minefield;
    private boolean firstReveal;
    private ActionHandler actionHandler;

    public Game(GridDimension dimensions, int totalMines, MineFieldFactory mineFieldFactory) {
        this.dimensions = dimensions;
        this.totalMines = totalMines;
        this.mineFieldFactory = mineFieldFactory;
        this.minefield = mineFieldFactory.createMineField(dimensions, 0);
        this.firstReveal = true;
        this.timer = new Timer();
        this.stats = new GameStatistics(dimensions.height(), dimensions.width(), totalMines);
        this.statusManager = new GameStatusManager();
    }

    public Game(Difficulty difficulty) {
        this(GridDimension.fromDifficulty(difficulty), difficulty.getMines(), new DefaultMineFieldFactory());
    }

    public MineField getMinefield() {
        return minefield;
    }

    public int getRevealed() {
        return stats.getRevealedCount();
    }

    public int getFlagsPlaced() {
        return stats.getFlagsPlaced();
    }

    public int getMinesLeft() {
        return stats.getMinesLeft();
    }

    public GameStatus getGameStatus() {
        return statusManager.getCurrentStatus();
    }

    public boolean getGameOver() {
        return statusManager.isGameOver();
    }

    public int getUnrevealedCount() {
        return stats.getUnrevealedCount();
    }

    public int getTotalMines() {
        return totalMines;
    }

    public long getElapsedTime() {
        return timer.getElapsedTime();
    }

    public void revealCell(Position position) {

        dimensions.validatePosition(position);

        if (firstReveal) {
            handleFirstReveal(position);
        }

        if (getGameOver()) {
            throw new InvalidGameOperationException("Cannot reveal cells after game is over!");
        }

        if (minefield.getCell(position).isFlagged()) {
            throw new InvalidGameOperationException("Cannot reveal a flagged cell! Remove flag first.");
        }

        if (minefield.getCell(position).isMined()) {
            if (actionHandler.revealSingleCell(position)) {
                stats.incrementRevealed();
                statusManager.endGame(GameStatus.LOST);
                timer.stop();
            }
            return;
        }

        int revealedCount = actionHandler.revealCascade(position);
        stats.incrementRevealed(revealedCount);

        if (stats.isGameWon()) {
            statusManager.endGame(GameStatus.WON);
            timer.stop();
        }
    }

    public void flagCell(Position position) {

        dimensions.validatePosition(position);

        if (firstReveal) {
            throw new InvalidGameOperationException("Flagging is not a valid first move!");
        }

        if (minefield.getCell(position).isRevealed()) {
            throw new InvalidGameOperationException("Cannot flag a revealed cell!");
        }

        if (minefield.getCell(position).isFlagged()) {
            minefield.flagCell(position);
            stats.decrementFlags();
        } else {
            minefield.flagCell(position);
            stats.incrementFlags();
        }
    }

    private void handleFirstReveal(Position position) {
        statusManager.startGame();
        this.minefield = mineFieldFactory.createMineField(dimensions, totalMines);
        this.minefield.initializeGrid(position);
        this.actionHandler = new ActionHandler(this.minefield);
        timer.start();
        firstReveal = false;
    }

    public void resetGame() {
        this.minefield = mineFieldFactory.createMineField(dimensions, totalMines);
        stats.reset();
        statusManager.resetGame();
        timer.reset();
        this.firstReveal = true;
        this.actionHandler = new ActionHandler(this.minefield);
    }

    public static class InvalidGameOperationException extends IllegalStateException {
        public InvalidGameOperationException(String message) {
            super(message);
        }
    }
}