package ms;

import java.time.Instant;

public class Game {

    private MineField minefield;
    private int revealedCells;
    private int flagsPlaced;
    private boolean gameOver;
    private Instant startTime;
    private Instant endTime;
    private boolean firstReveal;
    private final int height;
    private final int width;
    private final int totalMines;
    private final MineFieldFactory mineFieldFactory;

    public Game(int height, int width, int totalMines, MineFieldFactory mineFieldFactory) {
        this.height = height;
        this.width = width;
        this.totalMines = totalMines;
        this.mineFieldFactory = mineFieldFactory;
        this.minefield = mineFieldFactory.createMineField(height, width, 0);
        this.revealedCells = 0;
        this.flagsPlaced = 0;
        this.gameOver = false;
        this.startTime = null;
        this.endTime = null;
        this.firstReveal = true;
    }

    public Game(int height, int width, int totalMines) {
        this(height, width, totalMines, new DefaultMineFieldFactory());
    }

    public void placeMines(int firstRow, int firstCol) {
        this.minefield = mineFieldFactory.createMineField(height, width, totalMines);
        this.minefield.initializeGrid(firstRow, firstCol);
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

    public boolean getGameOver() {
        return gameOver;
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

    public void revealCell(int row, int col) {
        if (firstReveal) {
            this.placeMines(row, col);
            startTime = Instant.now();
            firstReveal = false;
        }

        if (gameOver || minefield.getCell(row, col).isRevealed()) {
            return;
        }


        if (minefield.getCell(row, col).isMined()) {
            minefield.getCell(row, col).reveal();
            this.revealedCells++;
            gameOver = true;
            endTime = Instant.now();
            return;
        }

        revealCellCascade(row, col);

        if (this.getUnrevealedCount() == totalMines) {
            gameOver = true;
            endTime = Instant.now();
        }
    }

    private void revealCellCascade(int row, int col) {
        if (gameOver || !minefield.isValid(row, col) || minefield.getCell(row, col).isRevealed()) {
            return;
        }

        if (minefield.getCell(row, col).isMined()) {
            return;
        }

        minefield.getCell(row, col).reveal();
        this.revealedCells++;

        if (minefield.countAdjacentMines(row, col) == 0) {
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    if (r == row && c == col) continue; // Skip the current cell
                    revealCellCascade(r, c);
                }
            }
        }
    }

    public void flagCell(int row, int col) {
        if (!minefield.getCell(row, col).isRevealed()) {
            if (minefield.getCell(row, col).isFlagged()) {
                minefield.flagCell(row, col);
                flagsPlaced--;
            } else {
                minefield.flagCell(row, col);
                flagsPlaced++;
            }
        }
    }

    public long getElapsedTime() {
        if (startTime != null) {
            return (endTime == null ? Instant.now() : endTime).toEpochMilli() - startTime.toEpochMilli();
        }
        return 0;
    }

    public void resetGame() {
        this.minefield = mineFieldFactory.createMineField(height, width, totalMines);
        this.revealedCells = 0;
        this.flagsPlaced = 0;
        this.gameOver = false;
        this.startTime = null;
        this.endTime = null;
        this.firstReveal = true;
    }
}