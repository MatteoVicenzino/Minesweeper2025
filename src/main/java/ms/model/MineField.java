package ms.model;

import java.util.Random;

/**
 * The {@code MineField} class manages the grid of cells, mine placement, and adjacency calculations.
 * It handles minefield initialization and provides access to individual cells.
 */
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

    public Cell getCell(Position position) {
        return field[position.row()][position.col()];
    }

    public void initializeGrid(Position firstClickPosition) {
        placeMinesRandomly(firstClickPosition);
    }

    public int countAdjacentMines(Position center) {

        dimensions.validatePosition(center);

        int mineCount = 0;
        for (Position adjacent : Position.getAdjacentPositions(center)) {
            if (dimensions.isValidPosition(adjacent) && getCell(adjacent).isMined()) {
                mineCount++;
            }
        }
        return mineCount;
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