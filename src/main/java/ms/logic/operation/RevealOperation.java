package ms.logic.operation;

import ms.logic.status.GameStatistics;
import ms.logic.status.GameStatus;
import ms.logic.status.GameStatusManager;
import ms.logic.status.Timer;
import ms.model.Cell;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;

/**
 * The {@code RevealOperation} class implements the {@code GameOperation} interface
 * to handle revealing cells in a Minesweeper game.
 * It manages the logic for revealing cells, handling mine reveals, and cascading reveals.
 */
public class RevealOperation implements GameOperation {

    private final MineField mineField;
    private final GridDimension dimensions;
    private final GameStatistics stats;
    private final GameStatusManager statusManager;
    private final Timer timer;

    /**
     * Constructor for RevealOperation.
     *
     * @param mineField The minefield containing the cells to be revealed.
     * @param dimensions The dimensions of the grid.
     * @param stats The game statistics to track revealed cells and game status.
     * @param statusManager The manager for handling game status changes.
     * @param timer The timer for tracking game time.
     */
    public RevealOperation(MineField mineField, GridDimension dimensions, GameStatistics stats, GameStatusManager statusManager, Timer timer) {
        this.mineField = mineField;
        this.dimensions = dimensions;
        this.stats = stats;
        this.statusManager = statusManager;
        this.timer = timer;
    }

    /**
     * Executes the reveal operation on the specified position.
     * If the cell is mined, it handles the mine reveal logic.
     * If the cell is not mined, it reveals the cell and cascades reveals if necessary.
     *
     * @param position The position of the cell to be revealed.
     */
    @Override
    public void execute(Position position) {
        if (mineField.getCell(position).isMined()) {
            handleMineReveal(position);
            return;
        }

        int revealedCount = revealCascade(position);
        stats.incrementRevealed(revealedCount);

        if (stats.isGameWon()) {
            statusManager.endGame(GameStatus.WON);
            timer.stop();
        }

    }

    /**
     * Handles the logic when a mine is revealed.
     * Reveals the cell and ends the game with a lost status.
     *
     * @param position The position of the mined cell that was revealed.
     */
    private void handleMineReveal(Position position) {
        if (revealSingleCell(position)) {
            stats.incrementRevealed();
            statusManager.endGame(GameStatus.LOST);
            timer.stop();
        }
    }

    /**
     * Reveals a single cell at the specified position.
     * If the cell is already revealed or flagged, it does nothing.
     *
     * @param position The position of the cell to be revealed.
     * @return true if the cell was successfully revealed, false otherwise.
     */
    private boolean revealSingleCell(Position position) {
        Cell cell = mineField.getCell(position);
        if (cell.isRevealed() || cell.isFlagged()) {
            return false;
        }

        cell.reveal();
        return cell.isRevealed();
    }

    /**
     * Reveals a cell and cascades the reveal to adjacent cells if the cell has no adjacent mines.
     * This method will recursively reveal all connected cells that can be revealed.
     *
     * @param startPosition The starting position for the cascade reveal.
     * @return The total number of cells revealed during the cascade.
     */
    private int revealCascade(Position startPosition) {
        if (!canCascadeRevealAt(startPosition)) {
            return 0;
        }

        int revealedCount = 0;

        if (revealSingleCell(startPosition)) {
            revealedCount++;
        }

        if (mineField.countAdjacentMines(startPosition) > 0) {
            return revealedCount;
        }

        for (Position adjacent : Position.getAdjacentPositions(startPosition)) {
            if (canCascadeRevealAt(adjacent) && !mineField.getCell(adjacent).isRevealed()) {
                revealedCount += revealCascade(adjacent);
            }
        }

        return revealedCount;
    }

    /**
     * Checks if a cell at the specified position can be cascaded for reveal.
     * A cell can be cascaded if it is not revealed, not flagged, and not mined.
     *
     * @param position The position of the cell to check.
     * @return true if the cell can be cascaded for reveal, false otherwise.
     */
    private boolean canCascadeRevealAt(Position position) {
        if (!dimensions.isValidPosition(position)) {
            return false;
        }

        Cell cell = mineField.getCell(position);
        return !cell.isRevealed() && !cell.isFlagged() && !cell.isMined();
    }
}