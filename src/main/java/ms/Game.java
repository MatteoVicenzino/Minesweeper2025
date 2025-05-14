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
        return totalMines-flagsPlaced;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void revealCell(int row, int col){
        getMinefield().revealCell(row, col);
        if (getMinefield().getCell(row, col).isMined()) {
            gameOver = true;
        } else {
            if (getMinefield().getUnrevealedCount() == getMinefield().getMines()) {
                gameOver = true;
            }
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
}
