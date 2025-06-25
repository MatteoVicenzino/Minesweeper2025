package ms.model;

/**
 * The {@code Difficulty} enum defines the three difficulty levels,
 * each with predefined grid dimensions and mine counts.
 */
public enum Difficulty {

    /**
     * The easy difficulty level with a 9x9 grid and 10 mines.
     */
    EASY(9, 9, 10),

    /**
     * The medium difficulty level with a 16x16 grid and 40 mines.
     */
    MEDIUM(16, 16, 40),

    /**
     * The hard difficulty level with a 16x30 grid and 99 mines.
     */
    HARD(16, 30, 99);

    private final int height;
    private final int width;
    private final int mines;

    /**
     * Constructs a {@code Difficulty} level with the specified grid dimensions and mine count.
     *
     * @param height the height of the grid for this difficulty
     * @param width the width of the grid for this difficulty
     * @param mines the number of mines for this difficulty
     */
    Difficulty(int height, int width, int mines) {
        this.height = height;
        this.width = width;
        this.mines = mines;
    }

    /**
     * Gets the height of the grid.
     *
     * @return the grid height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the grid.
     *
     * @return the grid width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the number of mines of the grid.
     *
     * @return the number of mines in the grid
     */
    public int getMines() {
        return mines;
    }
}