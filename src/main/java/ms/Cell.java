package ms;

public class Cell {
    private boolean isRevealed;
    private boolean isFlagged;
    private boolean isMined;

    public Cell() {
        this.isRevealed = false;
        this.isFlagged = false;
        this.isMined = false;
    }

    public boolean isMined() {
        return isMined;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

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

