package ms.model;

/**
 * The {@code Cell} class represents an individual cell in the minefield, contains state and mine information.
 */
public class Cell {
    private CellState status;
    private boolean isMined;

    public Cell() {
        this.status = CellState.HIDDEN;
        this.isMined = false;
    }

    /**
     * Toggles the flag state of this cell.
     */
    public void toggleFlag() {
        status = status.toggleFlag();
    }

    /**
     * Reveals this cell, changing its state based on whether it contains a mine.
     */
    public void reveal() {
        status = status.reveal(isMined);
    }

    /**
     * Checks if this cell contains a mine.
     *
     * @return {@code true} if the cell is mined, {@code false} otherwise
     */
    public boolean isMined() {
        return isMined;
    }

    /**
     * Sets the mine status of this cell.
     *
     * @param mined {@code true} to place a mine in this cell, {@code false} to remove it
     */
    public void setMined(boolean mined) {
        this.isMined = mined;
    }

    /**
     * Checks if this cell has been revealed (either normally or by explosion).
     *
     * @return {@code true} if the cell is revealed or exploded, {@code false} otherwise
     */
    public boolean isRevealed() {
        return status == CellState.REVEALED || status == CellState.EXPLODED;
    }

    /**
     * Checks if this cell is currently flagged.
     *
     * @return {@code true} if the cell is flagged, {@code false} otherwise
     */
    public boolean isFlagged() {
        return status == CellState.FLAGGED;
    }
}