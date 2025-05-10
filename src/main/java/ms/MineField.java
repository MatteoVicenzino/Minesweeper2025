package ms;

public class MineField {

    private int rows;
    private int cols;
    private int mines;
    private int revealed;

    // Constructor
    public MineField(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.revealed = 0;
    }

    // Getters
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMines() {
        return mines;
    }

    public int getRevealed() {
        return revealed;
    }

    public int getUnrevealedCount() {
        return (rows * cols) - revealed;
    }
}
