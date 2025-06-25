package ms.model;

/**
 * The {@code Position} record represents a coordinate pair (row, col) on the minefield grid.
 * It provides utility methods for calculating adjacent positions around a center point.
 */
public record Position(int row, int col) {

    /**
     * Returns an array of the eight positions adjacent to the given center position.
     *
     * @param center the center position to find adjacent positions for
     * @return an array of eight adjacent {@code Position} objects
     */
    public static Position[] getAdjacentPositions(Position center) {
        Position[] adjacent = new Position[8];
        int index = 0;

        for (int r = center.row() - 1; r <= center.row() + 1; r++) {
            for (int c = center.col() - 1; c <= center.col() + 1; c++) {
                if (r != center.row() || c != center.col()) {
                    adjacent[index++] = new Position(r, c);
                }
            }
        }

        return adjacent;
    }
}
