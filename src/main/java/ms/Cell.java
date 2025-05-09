package ms;

public class Cell {
    private boolean isRevealed;
    private boolean isFlagged;
    private boolean isMined;

    // Constructor
    public Cell() {
        this.isRevealed = false;
        this.isFlagged = false;
        this.isMined = false;
    }

    // Return if the cell has a mine
    public boolean isMined() {
        return isMined;
    }

    // Return if the cell is revealed
    public boolean isRevealed() {
        return isRevealed;
    }

    // Return if the cell is flagged
    public boolean isFlagged() {
        return isFlagged;
    }

    public void setMined(boolean value) {
        this.isMined = value;
    }

    public boolean reveal() {
        if (!isRevealed){
            this.isRevealed = true;
            return true;
        }
        return false;
    }

    public void toggleFlag() {
        if (!isRevealed) {
            this.isFlagged = !this.isFlagged;
        }
    }
}

