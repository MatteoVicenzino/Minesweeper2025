package ms.model;

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

    public boolean isRevealed() {
        return status == CellState.REVEALED || status == CellState.EXPLODED;
    }

    public boolean isFlagged() {
        return status == CellState.FLAGGED;
    }

    public void setMined(boolean mined) {
        this.isMined = mined;
    }
}