package ms;

import java.util.Random;

public class MineField {

    private int rows;
    private int cols;
    private int mines;
    private int revealed;
    private Cell[][] field;

    // Constructor
    public MineField(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.revealed = 0;
        this.field = new Cell[rows][cols];

        // Initialize the field with cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                field[i][j] = new Cell();
            }
        }
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

    public Cell getCell(int row, int col) {
        if (isValid(row, col)) {
            return field[row][col];
        } else {
            throw new IndexOutOfBoundsException("Invalid cell coordinates");
        }
    }

    public boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public void initializeGrid(int firstRow, int firstCol) {
        Random random = new Random();
        int placedMines = 0;

        // Place mines randomly, avoiding the safe cell
        while (placedMines < mines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            // Skip if this is the first cell or already has a mine
            if ((row == firstRow && col == firstCol) || field[row][col].isMined()) {
                continue;
            }

            field[row][col].setMined(true);
            placedMines++;
        }
    }

    public int countAdjacentMines(int row, int col) {
        return 0;
    }
}
