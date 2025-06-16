package ms;

public class GameStatistics {
    private final int totalMines;
    private final int totalCells;
    private int revealedCells;
    private int flagsPlaced;

    public GameStatistics(int height, int width, int totalMines) {
        this.totalMines = totalMines;
        this.totalCells = height * width;
        this.revealedCells = 0;
        this.flagsPlaced = 0;
    }

    public void incrementRevealed() {
        revealedCells++;
    }

    public void incrementRevealed(int count) {
        revealedCells += count;
    }

    public void incrementFlags() {
        flagsPlaced++;
    }

    public void decrementFlags() {
        flagsPlaced--;
    }

    public void reset() {
        revealedCells = 0;
        flagsPlaced = 0;
    }

    public int getRevealedCount() {
        return revealedCells;
    }

    public int getFlagsPlaced() {
        return flagsPlaced;
    }

    public int getMinesLeft() {
        return totalMines - flagsPlaced;
    }

    public int getUnrevealedCount() {
        return totalCells - revealedCells;
    }

    public boolean isGameWon() {
        return getUnrevealedCount() == totalMines;
    }
}

