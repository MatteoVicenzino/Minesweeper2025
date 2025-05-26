package ms;

import java.util.Random;

public class MineField {

    private final int height;
    private final int width;
    private final int mines;
    private final Cell[][] field;

    public MineField(int height, int width, int mines) {
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.field = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = new Cell();
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMines() {
        return mines;
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

        while (placedMines < mines) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

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

        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = col - 1; c <= col + 1; c++) {
                if (r == row && c == col) continue;
                if (isValid(r, c) && field[r][c].isMined()) {
                    mineCount++;
                }
            }
        }

        return mineCount;
    }

    @Deprecated
    public boolean revealCell(int row, int col) {
        if (this.isValid(row, col)) {
            return this.getCell(row, col).reveal();
        } else {
            return false;
        }
    }

    public void flagCell(int row, int col) {
        if (this.isValid(row, col)) {
            this.getCell(row, col).toggleFlag();
        } // eventually complete with else statement to catch error
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MineField other = (MineField) obj;

        if (this.height != other.height || this.width != other.width) {
            return false;
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (this.field[i][j].isMined() != other.field[i][j].isMined()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + height;
        result = 31 * result + width;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result = 31 * result + (field[i][j].isMined() ? 1 : 0);
            }
        }
        return result;
    }


}
