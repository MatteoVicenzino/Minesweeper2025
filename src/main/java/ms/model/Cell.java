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

    public void toggleFlag() {
        status = status.toggleFlag();
    }

    public void reveal() {
        status = status.reveal(isMined);
    }

    public boolean isMined() {
        return isMined;
    }

    public void setMined(boolean mined) {
        this.isMined = mined;
    }

    public boolean isRevealed() {
        return status == CellState.REVEALED || status == CellState.EXPLODED;
    }

    public boolean isFlagged() {
        return status == CellState.FLAGGED;
    }
}