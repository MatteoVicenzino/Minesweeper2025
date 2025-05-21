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


    // Constructor
    public Game(int height, int width, int totalMines) {
        this.minefield = null;
        this.revealedCells = 0;
        this.flagsPlaced = 0;
        this.gameOver = false;
        this.startTime = null;
        this.endTime = null;
        this.firstReveal = true;
        this.height = height;
        this.width = width;
        this.totalMines = totalMines;

    }

    public void initMinefield(boolean randomInit, int firstRow, int firstCol) {
        this.minefield = new MineField(this.height, this.width, this.totalMines, randomInit, firstRow, firstCol);
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
        return totalMines-flagsPlaced;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getUnrevealedCount() {
        return (10 * 10) - this.revealedCells;
    }


    public void revealCell(int row, int col) {

        if (firstReveal) {
            this.initMinefield(true, row, col);
            startTime = Instant.now();
            firstReveal = false;
        }

        if (gameOver || minefield.getCell(row, col).isRevealed()) {
            return;
        }

        if (minefield.revealCell(row, col)) {
            this.revealedCells++;
        }

        if (minefield.getCell(row, col).isMined()) {
            gameOver = true;
            endTime = Instant.now();
        } else if (this.getUnrevealedCount() == totalMines) {
            gameOver = true;
            endTime = Instant.now();
        }
    }

    public void flagCell(int row, int col) {
        if (!getMinefield().getCell(row, col).isRevealed()) {
            if (getMinefield().getCell(row, col).isFlagged()) {
                getMinefield().getCell(row, col).toggleFlag();
                flagsPlaced--;
            } else {
                getMinefield().getCell(row, col).toggleFlag();
                flagsPlaced++;
            }
        }
        // when flagging a revealed cell nothing should happen
    }

    public long getElapsedTime() {
        if (startTime != null) {
            return (endTime == null ? Instant.now() : endTime).toEpochMilli() - startTime.toEpochMilli();
        }
        return 0;
    }

    public void resetGame() {
        this.minefield = new MineField(10, 10, totalMines, false, -1, -1);
        this.gameOver = false;
        this.flagsPlaced = 0;
        this.startTime = null;
        this.endTime = null;
        this.firstReveal = true;
    }
}
