package ms;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private MineField minefield;
    private GameStatus gameStatus;
    private boolean firstReveal;
    private final int height;
    private final int width;
    private final int totalMines;
    private final MineFieldFactory mineFieldFactory;
    private final GameTimer timer;
    private final GameStatistics stats;

    public Game(int height, int width, int totalMines, MineFieldFactory mineFieldFactory) {
        this.height = height;
        this.width = width;
        this.totalMines = totalMines;
        this.mineFieldFactory = mineFieldFactory;
        this.minefield = mineFieldFactory.createMineField(height, width, 0);
        this.gameStatus = GameStatus.NOT_STARTED;
        this.firstReveal = true;
        this.timer = new GameTimer();
        this.stats = new GameStatistics(height, width, totalMines);
    }

    public Game(Difficulty difficulty) {
        this(difficulty.getHeight(), difficulty.getWidth(), difficulty.getMines(), new DefaultMineFieldFactory());
    }

    public void placeMines(Position firstRevealPosition) {
        this.minefield = mineFieldFactory.createMineField(height, width, totalMines);
        this.minefield.initializeGrid(firstRevealPosition);
    }

    public MineField getMinefield() {
        return minefield;
    }

    public int getRevealed() {
        return stats.getRevealedCount();  // Era: return revealedCells;
    }

    public int getFlagsPlaced() {
        return stats.getFlagsPlaced();    // Era: return flagsPlaced;
    }

    public int getMinesLeft() {
        return stats.getMinesLeft();      // Era: return totalMines - flagsPlaced;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean getGameOver() {
        return gameStatus == GameStatus.WON || gameStatus == GameStatus.LOST;
    }


    public int getUnrevealedCount() {
        return stats.getUnrevealedCount();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public void revealCell(Position position) {
        if (firstReveal) {
            gameStatus = GameStatus.IN_PROGRESS;
            this.placeMines(position);
            timer.start();
            firstReveal = false;
        }

        if (getGameOver() || minefield.getCell(position).isRevealed() || minefield.getCell(position).isFlagged()) {
            return;
        }


        if (minefield.getCell(position).isMined()) {
            minefield.uncoverCell(position);
            stats.incrementRevealed();
            gameStatus = GameStatus.LOST;
            timer.stop();
            return;
        }

        revealCellCascade(position);

        if (stats.isGameWon()) {
            gameStatus = GameStatus.WON;
            timer.stop();
        }
    }

    private void revealCellCascade(Position position) {
        if (getGameOver() || !minefield.isValid(position) || minefield.getCell(position).isRevealed()) {
            return;
        }

        if (minefield.getCell(position).isMined()) {
            return;
        }

        minefield.uncoverCell(position);
        stats.incrementRevealed();

        if (minefield.countAdjacentMines(position) == 0) {
            for (int r = position.row() - 1; r <= position.row() + 1; r++) {
                for (int c = position.col() - 1; c <= position.col() + 1; c++) {
                    if (r == position.row() && c == position.col()) continue; // Skip the current cell
                    revealCellCascade(new Position(r, c));
                }
            }
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
        this.gameStatus = GameStatus.IN_PROGRESS;
        timer.reset();
        this.firstReveal = true;
    }
}
