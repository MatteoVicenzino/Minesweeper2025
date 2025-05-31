package ms;

interface CellInterface {
    boolean isMined();
    boolean isRevealed();
    boolean isFlagged();
    boolean markAsRevealed();
    void toggleFlag();
    void setMined(boolean value);
}

public class Cell implements CellInterface {
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

    public boolean markAsRevealed() {
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

