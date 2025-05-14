package ms;

import java.time.Instant;

public class Game {

    private MineField minefield;
    private int flagsPlaced = 0;
    private boolean gameOver;
    private int totalMines;
    private Instant startTime;
    private Instant endTime;
    private boolean firstReveal = true;

    // Constructor
    public Game() {
        this.minefield = null;
        this.totalMines = 10;
        this.gameOver = false;
        this.startTime = null;
        this.endTime = null;

        this.initMinefield();
    }

    public MineField initMinefield() {
        this.minefield = new MineField(10, 10, totalMines);
        return minefield;
    }

    public MineField getMinefield() {
        return minefield;
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

    public void revealCell(int row, int col) {
        if (gameOver || !minefield.isValid(row, col) || minefield.getCell(row, col).isRevealed()) {
            return;
        }

        if (firstReveal && !minefield.getCell(row, col).isMined()) {
            startTime = Instant.now();
            firstReveal = false;
        }

        minefield.revealCell(row, col);

        if (minefield.getCell(row, col).isMined()) {
            gameOver = true;
            endTime = Instant.now();
        } else if (minefield.getUnrevealedCount() == totalMines) {
            gameOver = true;
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
}
