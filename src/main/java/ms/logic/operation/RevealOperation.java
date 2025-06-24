package ms.logic.operation;

import ms.logic.status.GameStatistics;
import ms.logic.status.GameStatus;
import ms.logic.status.GameStatusManager;
import ms.logic.status.Timer;
import ms.model.Cell;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;

public class RevealOperation implements GameOperation {

    private final MineField mineField;
    private final GridDimension dimensions;
    private final GameStatistics stats;
    private final GameStatusManager statusManager;
    private final Timer timer;

    public RevealOperation(MineField mineField, GridDimension dimensions, GameStatistics stats, GameStatusManager statusManager, Timer timer) {
        this.mineField = mineField;
        this.dimensions = dimensions;
        this.stats = stats;
        this.statusManager = statusManager;
        this.timer = timer;
    }

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

    private void handleMineReveal(Position position) {
        if (revealSingleCell(position)) {
            stats.incrementRevealed();
            statusManager.endGame(GameStatus.LOST);
            timer.stop();
        }
    }

    private boolean revealSingleCell(Position position) {
        Cell cell = mineField.getCell(position);
        if (cell.isRevealed() || cell.isFlagged()) {
            return false;
        }

        cell.reveal();
        return cell.isRevealed();
    }

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

    private boolean canCascadeRevealAt(Position position) {
        if (!dimensions.isValidPosition(position)) {
            return false;
        }

        Cell cell = mineField.getCell(position);
        return !cell.isRevealed() && !cell.isFlagged() && !cell.isMined();
    }
}