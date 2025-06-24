package ms.logic;

import ms.logic.operation.FlagOperation;
import ms.logic.operation.GameOperation;
import ms.logic.operation.RevealOperation;
import ms.logic.rules.FlagRules;
import ms.logic.rules.GameRules;
import ms.logic.rules.RevealRules;
import ms.logic.status.GameStatistics;
import ms.logic.status.GameStatus;
import ms.logic.status.GameStatusManager;
import ms.logic.status.Timer;
import ms.model.Difficulty;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;

public class Game {

    private final GridDimension dimensions;
    private final int totalMines;
    private final GameStatusManager statusManager;
    private final MineFieldFactory mineFieldFactory;
    private final Timer timer;
    private final GameStatistics stats;
    private MineField minefield;
    private boolean isFirstReveal;

    public Game(GridDimension dimensions, int totalMines, MineFieldFactory mineFieldFactory) {
        this.dimensions = dimensions;
        this.totalMines = totalMines;
        this.mineFieldFactory = mineFieldFactory;
        this.minefield = mineFieldFactory.createMineField(dimensions, 0);
        this.isFirstReveal = true;
        this.timer = new Timer();
        this.stats = new GameStatistics(dimensions.height(), dimensions.width(), totalMines);
        this.statusManager = new GameStatusManager();
    }

    public Game(Difficulty difficulty) {
        this(GridDimension.fromDifficulty(difficulty), difficulty.getMines(), new DefaultMineFieldFactory());
    }

    public MineField getMinefield() {
        return minefield;
    }

    public int getRevealed() {
        return stats.getRevealedCount();
    }

    public int getFlagsPlaced() {
        return stats.getFlagsPlaced();
    }

    public int getMinesLeft() {
        return stats.getMinesLeft();
    }

    public GameStatus getGameStatus() {
        return statusManager.getCurrentStatus();
    }

    public boolean isGameOver() {
        return statusManager.isGameOver();
    }

    public int getUnrevealedCount() {
        return stats.getUnrevealedCount();
    }

    public int getTotalMines() {
        return totalMines;
    }

    public int getTotalOfNonMineCells() {
        return stats.getTotalOfNonMineCells();
    }

    public long getElapsedTime() {
        return timer.getElapsedTime();
    }

    public void revealCell(Position position) {
        if (isFirstReveal) {
            handleFirstReveal(position);
        }

        GameRules rules = new RevealRules(dimensions, minefield, statusManager);
        rules.validate(position);
        GameOperation revealOperation = new RevealOperation(minefield, dimensions, stats, statusManager, timer);
        revealOperation.execute(position);
    }

    public void flagCell(Position position) {
        GameRules rules = new FlagRules(dimensions, minefield, isFirstReveal);
        rules.validate(position);
        GameOperation flagOperation = new FlagOperation(minefield, stats);
        flagOperation.execute(position);
    }

    private void handleFirstReveal(Position position) {
        dimensions.validatePosition(position);
        statusManager.startGame();
        this.minefield = mineFieldFactory.createMineField(dimensions, totalMines);
        this.minefield.initializeGrid(position);
        timer.start();
        isFirstReveal = false;
    }

    public void resetGame() {
        this.minefield = mineFieldFactory.createMineField(dimensions, totalMines);
        stats.reset();
        statusManager.resetGame();
        timer.reset();
        this.isFirstReveal = true;
    }

    public static class InvalidGameOperationException extends IllegalStateException {
        public InvalidGameOperationException(String message) {
            super(message);
        }
    }
}