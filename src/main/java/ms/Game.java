package ms;

public class Game {

    private MineField minefield;
    private int minesLeft;
    private boolean gameOver;

    // Constructor
    public Game() {
        this.minefield = null;
        this.minesLeft = 10;
        this.gameOver = false;

        this.initMinefield();
    }

    public MineField initMinefield() {
        this.minefield = new MineField(10, 10, minesLeft);
        return minefield;
    }

    public MineField getMinefield() {
        return minefield;
    }

    public int getMinesLeft() {
        return minesLeft;
    }

    public boolean getGameOver() {
        return gameOver;
    }
}
