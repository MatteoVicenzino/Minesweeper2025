package ms;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private MineField minefield;
    private int revealedCells;
    private int flagsPlaced;
    private GameStatus gameStatus;
    private boolean firstReveal;
    private final int height;
    private final int width;
    private final int totalMines;
    private final MineFieldFactory mineFieldFactory;
    private final GameTimer timer;

    public Game(int height, int width, int totalMines, MineFieldFactory mineFieldFactory) {
        this.height = height;
        this.width = width;
        this.totalMines = totalMines;
        this.mineFieldFactory = mineFieldFactory;
        this.minefield = mineFieldFactory.createMineField(height, width, 0);
        this.revealedCells = 0;
        this.flagsPlaced = 0;
        this.gameStatus = GameStatus.NOT_STARTED;
        this.firstReveal = true;
        this.timer = new GameTimer();
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
        return revealedCells;
    }

    public int getFlagsPlaced() {
        return flagsPlaced;
    }

    public int getMinesLeft() {
        return totalMines - flagsPlaced;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean getGameOver() {
        return gameStatus == GameStatus.WON || gameStatus == GameStatus.LOST;
    }


    public int getUnrevealedCount() {
        return (height * width) - this.revealedCells;
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
            this.revealedCells++;
            gameStatus = GameStatus.LOST;
            timer.stop();
            return;
        }

        revealCellCascade(position);

        if (this.getUnrevealedCount() == totalMines) {
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
        this.revealedCells++;

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
                flagsPlaced--;
            } else {
                minefield.flagCell(position);
                flagsPlaced++;
            }
        }
    }

    public long getElapsedTime() {
        return timer.getElapsedTime();
    }

    public void resetGame() {
        this.minefield = mineFieldFactory.createMineField(height, width, totalMines);
        this.revealedCells = 0;
        this.flagsPlaced = 0;
        this.gameStatus = GameStatus.IN_PROGRESS;
        timer.reset();
        this.firstReveal = true;
    }
}
