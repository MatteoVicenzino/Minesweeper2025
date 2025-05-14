package ms;

public class Game {

    private MineField minefield;
    private int flagsPlaced = 0;
    private boolean gameOver;
    private int totalMines;

    // Constructor
    public Game() {
        this.minefield = null;
        this.totalMines = 10;
        this.gameOver = false;

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
        return totalMines;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void revealCell(int row, int col){
        getMinefield().revealCell(row, col);
    }

    public void flagCell(int row, int col){
        if (getMinefield().isValid(row, col) && !getMinefield().getCell(row, col).isRevealed()) {
            boolean wasFlagged = getMinefield().getCell(row, col).isFlagged();
            getMinefield().flagCell(row, col);
            boolean isNowFlagged = getMinefield().getCell(row, col).isFlagged();

            if (!wasFlagged && isNowFlagged) {
                flagsPlaced++;
            } else if (wasFlagged && !isNowFlagged) {
                flagsPlaced--;
            }
        }
    }
}
