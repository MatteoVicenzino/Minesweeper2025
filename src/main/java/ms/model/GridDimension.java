package ms.model;

/**
 * The {@code GridDimension} record defines the dimensions a minefield grid.
 * It provides methods for position validation.
 */
public record GridDimension(int height, int width) {

    /**
     * Constructs a {@code GridDimension} with the specified height and width.
     * Both dimensions must be positive values.
     *
     * @param height the height of the grid
     * @param width the width of the grid
     * @throws IllegalArgumentException if height or width is not positive
     */
    public GridDimension {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive, got: " + height);
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive, got: " + width);
        }
    }

    /**
     * Creates a {@code GridDimension} from the specified difficulty level.
     *
     * @param difficulty the {@code Difficulty} level
     * @return a {@code GridDimension} with the appropriate dimensions for the difficulty
     */
    public static GridDimension fromDifficulty(Difficulty difficulty) {
        return new GridDimension(difficulty.getHeight(), difficulty.getWidth());
    }

    /**
     * Calculates the total number of cells in the grid.
     *
     * @return the total number of cells (height × width)
     */
    public int totalCells() {
        return height * width;
    }

    /**
     * Checks if the given position is within the valid bounds of the grid.
     *
     * @param position the {@code Position} to validate
     * @return {@code true} if the position is valid, {@code false} otherwise
     */
    public boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < height &&
                position.col() >= 0 && position.col() < width;
    }

    /**
     * Validates that the given position is within the grid bounds,
     * and throws {@code IndexOutOfBoundsException} if is not.
     *
     * @param position the {@code Position} to validate
     * @throws IndexOutOfBoundsException if the position is outside the valid range
     */
    public void validatePosition(Position position) {
        if (!isValidPosition(position)) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid cell coordinates: (%d, %d). Valid range: (0,0) to (%d,%d)",
                            position.row(), position.col(), height - 1, width - 1));
        }
    }
}