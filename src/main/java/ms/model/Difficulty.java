package ms.model;

public enum Difficulty {

    EASY(9, 9, 10),
    MEDIUM(16, 16, 40),
    HARD(16, 30, 99);

    private final int height;
    private final int width;
    private final int mines;

    Difficulty(int height, int width, int mines) {
        this.height = height;
        this.width = width;
        this.mines = mines;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMines() {
        return mines;
    }
}