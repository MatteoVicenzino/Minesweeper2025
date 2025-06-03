package ms;

import java.time.Instant;

public class Game {

    private MineField minefield;
    private int revealedCells;
    private int flagsPlaced;
    private GameStatus gameStatus;
    private Instant startTime;
    private Instant endTime;
    private boolean firstReveal;
    private final int height;
    private final int width;
    private final int totalMines;
    private final MineFieldFactory mineFieldFactory;

    public Game(int height, int width, int totalMines, MineFieldFactory mineFieldFactory) {
        this.height = height;
        this.width = width;
        this.totalMines = totalMines;
        this.mineFieldFactory = mineFieldFactory;
        this.minefield = mineFieldFactory.createMineField(height, width, 0);
        this.revealedCells = 0;
        this.flagsPlaced = 0;
        this.gameStatus = GameStatus.NOT_STARTED;
        this.startTime = null;
        this.endTime = null;
        this.firstReveal = true;
    }

    public Game(Difficulty difficulty) {
        this(difficulty.getHeight(), difficulty.getWidth(), difficulty.getMines(), new DefaultMineFieldFactory());
    }

    public void placeMines(Position firstRevealPosition) {
        this.minefield = mineFieldFactory.createMineField(height, width, totalMines);
        this.minefield.initializeGrid(firstRevealPosition);
    }

    public MineField getMinefield() {
        return minefield;
    }

    public int getRevealed() {
        return revealedCells;
    }

    public int getFlagsPlaced() {
        return flagsPlaced;
    }

    public int getMinesLeft() {
        return totalMines - flagsPlaced;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public boolean getGameOver() {
        return gameStatus == GameStatus.WON || gameStatus == GameStatus.LOST;
    }


    public int getUnrevealedCount() {
        return (height * width) - this.revealedCells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getTotalMines() {
        return totalMines;
    }

    public void revealCell(Position position) {
        if (firstReveal) {
            gameStatus = GameStatus.IN_PROGRESS;
            this.placeMines(position);
            startTime = Instant.now();
            firstReveal = false;
        }

        if (getGameOver() || minefield.getCell(position).isRevealed() || minefield.getCell(position).isFlagged()) {
            return;
        }


        if (minefield.getCell(position).isMined()) {
            minefield.uncoverCell(position);
            this.revealedCells++;
            gameStatus = GameStatus.LOST;
            endTime = Instant.now();
            return;
        }

        revealCellCascade(position);

        if (this.getUnrevealedCount() == totalMines) {
            gameStatus = GameStatus.WON;
            endTime = Instant.now();
        }
    }

    private void revealCellCascade(Position position) {
        if (getGameOver() || !minefield.isValid(position) || minefield.getCell(position).isRevealed()) {
            return;
        }

        if (minefield.getCell(position).isMined()) {
            return;
        }

        minefield.uncoverCell(position);
        this.revealedCells++;

        if (minefield.countAdjacentMines(position) == 0) {
            for (int r = position.row() - 1; r <= position.row() + 1; r++) {
                for (int c = position.col() - 1; c <= position.col() + 1; c++) {
                    if (r == position.row() && c == position.col()) continue; // Skip the current cell
                    revealCellCascade(new Position(r, c));
                }
            }
        }
    }

    public void flagCell(Position position) {
        if (!minefield.getCell(position).isRevealed()) {
            if (minefield.getCell(position).isFlagged()) {
                minefield.flagCell(position);
                flagsPlaced--;
            } else {
                minefield.flagCell(position);
                flagsPlaced++;
            }
        }
    }

    public long getElapsedTime() {
        if (startTime != null) {
            return (endTime == null ? Instant.now() : endTime).toEpochMilli() - startTime.toEpochMilli();
        }
        return 0;
    }

    public void resetGame() {
        this.minefield = mineFieldFactory.createMineField(height, width, totalMines);
        this.revealedCells = 0;
        this.flagsPlaced = 0;
        this.gameStatus = GameStatus.IN_PROGRESS;
        this.startTime = null;
        this.endTime = null;
        this.firstReveal = true;
    }
}