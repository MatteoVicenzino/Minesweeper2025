package ms.logic;

import ms.model.*;

public class Game {

    private MineField minefield;
    private boolean firstReveal;
    private final GridDimension dimensions;
    private final int totalMines;
    private final GameStatusManager statusManager;
    private final MineFieldFactory mineFieldFactory;
    private final Timer timer;
    private final GameStatistics stats;
    private CellRevealHandler revealHandler;

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

    public void placeMines(Position firstRevealPosition) {
        this.minefield = mineFieldFactory.createMineField(dimensions, totalMines);
        this.minefield.initializeGrid(firstRevealPosition);
        this.revealHandler = new CellRevealHandler(this.minefield);
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

    public void revealCell(Position position) {

        dimensions.validatePosition(position);

        if (firstReveal) {
            statusManager.startGame();
            this.placeMines(position);
            timer.start();
            firstReveal = false;
        }

        if (getGameOver()) {
            return;
        }

        if (minefield.getCell(position).isFlagged()) {
            return;
        }

        if (minefield.getCell(position).isMined()) {
            if (revealHandler.revealSingleCell(position)) {
                stats.incrementRevealed();
                statusManager.endGameWithLoss();
                timer.stop();
            }
            return;
        }

        int revealedCount = revealHandler.revealCascade(position);
        stats.incrementRevealed(revealedCount);

        if (stats.isGameWon()) {
            statusManager.endGameWithWin();
            timer.stop();
        }
    }

    public void flagCell(Position position) {

        dimensions.validatePosition(position);

        if (firstReveal) {
            throw new IllegalStateException("Flagging is not a valid first move!");
        }

        if (!minefield.getCell(position).isRevealed()) {
            if (minefield.getCell(position).isFlagged()) {
                minefield.flagCell(position);
                stats.decrementFlags();
            } else {
                minefield.flagCell(position);
                stats.incrementFlags();
            }
        }
    }

    public long getElapsedTime() {
        return timer.getElapsedTime();
    }

    public void resetGame() {
        this.minefield = mineFieldFactory.createMineField(dimensions, totalMines);
        stats.reset();
        statusManager.resetGame();
        timer.reset();
        this.firstReveal = true;
        this.revealHandler = new CellRevealHandler(this.minefield);
    }
}