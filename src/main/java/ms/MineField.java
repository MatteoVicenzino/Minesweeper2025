package ms;
import ms.cell.Cell;
import java.util.Random;

public class MineField {

    private final GridDimension dimensions;
    private final int mines;
    private final Cell[][] field;

    public MineField(GridDimension dimensions, int mines) {
        this.dimensions = dimensions;
        this.mines = mines;
        this.field = new Cell[dimensions.height()][dimensions.width()];

        initializeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < dimensions.height(); i++) {
            for (int j = 0; j < dimensions.width(); j++) {
                field[i][j] = new Cell();
            }
        }
    }

    private void placeMinesRandomly(Position excludePosition) {
        Random random = new Random();
        int placedMines = 0;

        while (placedMines < mines) {
            int row = random.nextInt(dimensions.height());
            int col = random.nextInt(dimensions.width());

            Position candidate = new Position(row, col);

            // Skip if the candidate position is the excluded position or already mined
            if (candidate.equals(excludePosition) || field[row][col].isMined()) {
                continue;
            }

            field[row][col].setMined(true);
            placedMines++;
        }
    }

    public int getHeight() {
        return dimensions.height();
    }

    public int getWidth() {
        return dimensions.width();
    }

    public int getMines() {
        return mines;
    }

    public GridDimension getDimensions() {
        return dimensions;
    }

    public boolean isValid(Position position) {
        return dimensions.isValidPosition(position);
    }

    public Cell getCell(Position position) {
        dimensions.validatePosition(position);
        return field[position.row()][position.col()];
    }

    public void initializeGrid(Position firstClickPosition) {
        dimensions.validatePosition(firstClickPosition);
        placeMinesRandomly(firstClickPosition);
    }

    public int countAdjacentMines(Position position) {
        dimensions.validatePosition(position);

        int mineCount = 0;
        for (int r = position.row() - 1; r <= position.row() + 1; r++) {
            for (int c = position.col() - 1; c <= position.col() + 1; c++) {
                if (r == position.row() && c == position.col()) continue;

                Position adjacentPos = new Position(r, c);
                if (dimensions.isValidPosition(adjacentPos) && field[r][c].isMined()) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    public void revealCell(Position position) {
        if (dimensions.isValidPosition(position)) {
            this.getCell(position).reveal();
        }
    }

    public void flagCell(Position position) {
        if (dimensions.isValidPosition(position)) {
            getCell(position).toggleFlag();
        }
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

        if (!this.dimensions.equals(other.dimensions)) {
            return false;
        }

        for (int i = 0; i < dimensions.height(); i++) {
            for (int j = 0; j < dimensions.width(); j++) {
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
        result = 31 * result + dimensions.hashCode();
        for (int i = 0; i < dimensions.height(); i++) {
            for (int j = 0; j < dimensions.width(); j++) {
                result = 31 * result + (field[i][j].isMined() ? 1 : 0);
            }
        }
        return result;
    }
}