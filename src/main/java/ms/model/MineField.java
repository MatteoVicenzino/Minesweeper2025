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

    /**
     * Constructs a new {@code MineField} with the specified dimensions and mine count.
     * Initializes all cells but does not place mines until {@code initializeGrid} is called.
     *
     * @param dimensions the {@code GridDimension} defining the size of the minefield
     * @param mines the number of mines to be placed in the field
     */
    public MineField(GridDimension dimensions, int mines) {
        this.dimensions = dimensions;
        this.mines = mines;
        this.field = new Cell[dimensions.height()][dimensions.width()];

        initializeCells();
    }

    /**
     * Initializes all cells in the grid to their default (empty) state.
     */
    private void initializeCells() {
        for (int i = 0; i < dimensions.height(); i++) {
            for (int j = 0; j < dimensions.width(); j++) {
                field[i][j] = new Cell();
            }
        }
    }

    /**
     * Places mines randomly in the field, excluding the specified position.
     * This ensures the first clicked position is always safe.
     *
     * @param excludePosition the position to exclude from mine placement
     */
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

    /**
     * @return the number of rows in the field
     */
    public int getHeight() {
        return dimensions.height();
    }

    /**
     * @return the number of columns in the field
     */
    public int getWidth() {
        return dimensions.width();
    }

    /**
     * @return the number of mines in the field
     */
    public int getMines() {
        return mines;
    }

    /**
     * Gets the cell at the specified position.
     *
     * @param position the {@code Position} of the desired cell
     * @return the {@code Cell} at the given position
     */
    public Cell getCell(Position position) {
        return field[position.row()][position.col()];
    }

    /**
     * Initializes the minefield by placing mines randomly, excluding the first click position.
     * This method should be called when the first cell is revealed.
     *
     * @param firstClickPosition the position of the first click, which will be mine-free
     */
    public void initializeGrid(Position firstClickPosition) {
        placeMinesRandomly(firstClickPosition);
    }

    /**
     * Counts the number of mines adjacent to the specified position.
     *
     * @param center the center position to count around
     * @return the number of adjacent mines (0-8)
     * @throws IndexOutOfBoundsException if the center position is invalid
     */
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

    /**
     * Compares this minefield with another object for equality.
     * Two minefields are equal if they have the same dimensions and mine placement.
     *
     * @param obj the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
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

    /**
     * Returns a hash code for this minefield based on its dimensions and mine placement.
     *
     * @return the hash code value
     */
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