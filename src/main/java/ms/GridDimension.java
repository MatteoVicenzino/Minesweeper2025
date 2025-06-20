package ms;

public record GridDimension(int height, int width) {

    public GridDimension {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive, got: " + height);
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive, got: " + width);
        }
    }

    public int totalCells() {
        return height * width;
    }

    public static GridDimension fromDifficulty(Difficulty difficulty) {
        return new GridDimension(difficulty.getHeight(), difficulty.getWidth());
    }

    public boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < height &&
                position.col() >= 0 && position.col() < width;
    }

    public void validatePosition(Position position) {
        if (!isValidPosition(position)) {
            throw new IndexOutOfBoundsException(
                    String.format("Invalid cell coordinates: (%d, %d). Valid range: (0,0) to (%d,%d)",
                            position.row(), position.col(), height - 1, width - 1));
        }
    }
}