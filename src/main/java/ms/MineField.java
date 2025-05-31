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

        initializeCells();
    }

    private void initializeCells() {
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

    public boolean isValid(Position position) {
        return position.row() >= 0 && position.row() < height && position.col() >= 0 && position.col() < width;
    }

    public Cell getCell(Position position) {
        validatePosition(position);
        return field[position.row()][position.col()];
    }

    public void initializeGrid(Position firstClickPosition) {
        placeMinesRandomly(firstClickPosition);
    }

    public int countAdjacentMines(Position position) {
        validatePosition(position);

        int mineCount = 0;
        for (int r = position.row() - 1; r <= position.row() + 1; r++) {
            for (int c = position.col() - 1; c <= position.col() + 1; c++) {
                if (r == position.row() && c == position.col()) continue;
                if (isValid(new Position(r, c)) && field[r][c].isMined()) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    public void uncoverCell(Position position) {
        if (this.isValid(position)) {
            this.getCell(position).markAsRevealed();
        }
    }

    public void flagCell(Position position) {
        if (isValid(position)) {
            getCell(position).toggleFlag();
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


    private void validatePosition(Position position) {
        if (!isValid(position)) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid cell coordinates: (%d, %d)",
                            position.row(), position.col()));
        }
    }

    private void placeMinesRandomly(Position excludePosition) {
        Random random = new Random();
        int placedMines = 0;

        while (placedMines < mines) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            // Non piazzare mine sulla prima cella cliccata o dove già c'è una mina
            if (isCellExcluded(row, col, excludePosition) || field[row][col].isMined()) {
                continue;
            }

            field[row][col].setMined(true);
            placedMines++;
        }
    }

    private boolean isCellExcluded(int row, int col, Position excludePosition) {
        return row == excludePosition.row() && col == excludePosition.col();
    }
}
