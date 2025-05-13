package ms;

import java.util.Random;

public class MineField {

    private int height;
    private int width;
    private int mines;
    private int revealed;
    private Cell[][] field;

    // Constructor
    public MineField(int height, int width, int mines) {
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.revealed = 0;
        this.field = new Cell[height][width];

        // Initialize the field with cells
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = new Cell();
            }
        }
    }

    // Getters
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMines() {
        return mines;
    }

    public int getRevealed() {
        return revealed;
    }

    public int getUnrevealedCount() {
        return (height * width) - revealed;
    }

    public Cell getCell(int row, int col) {
        if (isValid(row, col)) {
            return field[row][col];
        } else {
            throw new IndexOutOfBoundsException("Invalid cell coordinates");
        }
    }

    public boolean isValid(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    public void initializeGrid(int firstRow, int firstCol) {
        Random random = new Random();
        int placedMines = 0;

        // Place mines randomly, avoiding the safe cell
        while (placedMines < mines) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            // Skip if this is the first cell or already has a mine
            if ((row == firstRow && col == firstCol) || field[row][col].isMined()) {
                continue;
            }

            field[row][col].setMined(true);
            placedMines++;
        }
    }

    public int countAdjacentMines(int row, int col) {
        if (!isValid(row, col)) {
            throw new IndexOutOfBoundsException("Invalid cell coordinates");
        }
        int mineCount = 0;

        // Check all adjacent cells
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r == row && c == col) continue; // Skip the current cell
                if (isValid(r, c) && field[r][c].isMined()) {
                    mineCount++;
                }
            }
        }

        return mineCount;
    }

    public void revealCell(int row, int col) {
        if (this.isValid(row, col)) {
            Cell cell = this.getCell(row, col);
            if (cell.reveal()) {
                this.revealed++;
            }
        }
    }
}
