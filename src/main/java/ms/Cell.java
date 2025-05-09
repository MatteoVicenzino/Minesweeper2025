package ms;

public class Cell {
    private boolean isRevealed;
    private boolean isFlagged;
    private boolean isBomb;

    // Constructor
    public Cell() {
        this.isRevealed = false;
        this.isFlagged = false;
        this.isBomb = false;
    }

    // Return if the cell has a bomb
    public boolean isBomb() {
        return isBomb;
    }

    // Return if the cell is revealed
    public boolean isRevealed() {
        return isRevealed;
    }

    // Return if the cell is flagged
    public boolean isFlagged() {
        return isFlagged;
    }

    public void setBomb(boolean value) {
        this.isBomb = value;
    }

    public boolean Reveal() {
        if (!isRevealed){
            this.isRevealed = true;
            return true;
        }
        return false;
    }

    public void toggleFlag() {
        this.isFlagged = !this.isFlagged;
    }
}

