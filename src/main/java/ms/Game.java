package ms;

import ms.CellRevealHandler;

public class Game {

    private MineField minefield;
    private boolean firstReveal;
    private final int height;
    private final int width;
    private final int totalMines;
    private final GameStatusManager statusManager;
    private final MineFieldFactory mineFieldFactory;
    private final GameTimer timer;
    private final GameStatistics stats;
    private CellRevealHandler revealHandler;

    public Game(int height, int width, int totalMines, MineFieldFactory mineFieldFactory) {
        this.height = height;
        this.width = width;
        this.totalMines = totalMines;
        this.mineFieldFactory = mineFieldFactory;
        this.minefield = mineFieldFactory.createMineField(height, width, 0);
        this.firstReveal = true;
        this.timer = new GameTimer();
        this.stats = new GameStatistics(height, width, totalMines);
        this.statusManager = new GameStatusManager();
    }

    public Game(Difficulty difficulty) {
        this(difficulty.getHeight(), difficulty.getWidth(), difficulty.getMines(), new DefaultMineFieldFactory());
    }

    public void placeMines(Position firstRevealPosition) {
        this.minefield = mineFieldFactory.createMineField(height, width, totalMines);
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

        if (position.row() < 0 || position.row() >= height ||
                position.col() < 0 || position.col() >= width) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid cell coordinates: (%d, %d). Valid range: (0,0) to (%d,%d)",
                            position.row(), position.col(), height-1, width-1));
        }

        if (firstReveal){
            statusManager.startGame();
            this.placeMines(position);
            timer.start();
            firstReveal = false;
        }

        if (getGameOver()) {
            return;
        }


        if (minefield.getCell(position).isMined()) {
            minefield.revealCell(position);
            stats.incrementRevealed();
            statusManager.endGameWithLoss();
            timer.stop();
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
        this.minefield = mineFieldFactory.createMineField(height, width, totalMines);
        stats.reset();
        statusManager.resetGame();
        timer.reset();
        this.firstReveal = true;
        this.revealHandler = new CellRevealHandler(this.minefield);
    }
}
