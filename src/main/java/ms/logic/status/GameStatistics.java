package ms.logic.status;

import ms.model.GridDimension;

/**
 * Tracks and manages game statistics for a Minesweeper game, including mine count,
 * revealed cells, flag placements, and win conditions.
 */
public class GameStatistics {

    private final int totalMines;
    private final int totalCells;
    private int revealedCells;
    private int flagsPlaced;

    /**
     * Constructs a new GameStatistics instance with the given grid dimensions and mine count.
     *
     * @param height the height of the game grid
     * @param width the width of the game grid
     * @param totalMines the total number of mines in the game
     */
    public GameStatistics(int height, int width, int totalMines) {
        this(new GridDimension(height, width), totalMines);
    }

    /**
     * Constructs a new GameStatistics instance with the given grid dimensions and mine count.
     *
     * @param dimensions the dimensions of the game grid
     * @param totalMines the total number of mines in the game
     */
    public GameStatistics(GridDimension dimensions, int totalMines) {
        this.totalMines = totalMines;
        this.totalCells = dimensions.totalCells();
        this.revealedCells = 0;
        this.flagsPlaced = 0;
    }

    /**
     * Increments the count of revealed cells by 1.
     */
    public void incrementRevealed() {
        revealedCells++;
    }

    /**
     * Increments the count of revealed cells by the specified amount.
     *
     * @param count the number of cells to add to the revealed count
     */
    public void incrementRevealed(int count) {
        revealedCells += count;
    }

    /**
     * Increments the count of placed flags by 1.
     */
    public void incrementFlags() {
        flagsPlaced++;
    }

    /**
     * Decrements the count of placed flags by 1.
     */
    public void decrementFlags() {
        flagsPlaced--;
    }

    /**
     * Resets all game statistics to their initial state.
     */
    public void reset() {
        revealedCells = 0;
        flagsPlaced = 0;
    }

    /**
     * Gets the number of revealed cells.
     *
     * @return the count of revealed cells
     */
    public int getRevealedCount() {
        return revealedCells;
    }

    /**
     * Gets the number of flags placed by the player.
     *
     * @return the count of placed flags
     */
    public int getFlagsPlaced() {
        return flagsPlaced;
    }

    /**
     * Calculates the number of mines remaining to be flagged.
     *
     * @return the difference between total mines and flags placed
     */
    public int getMinesLeft() {
        return totalMines - flagsPlaced;
    }

    /**
     * Gets the total number of non-mine cells in the game.
     *
     * @return the total cells minus total mines
     */
    public int getTotalOfNonMineCells() {
        return totalCells - totalMines;
    }

    /**
     * Calculates the number of unrevealed cells.
     *
     * @return the difference between total cells and revealed cells
     */
    public int getUnrevealedCount() {
        return totalCells - revealedCells;
    }

    /**
     * Determines if the game has been won.
     * The game is won when all non-mine cells have been revealed.
     *
     * @return true if all non-mine cells are revealed, false otherwise
     */
    public boolean isGameWon() {
        return getUnrevealedCount() == totalMines;
    }
}