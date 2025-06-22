package ms.logic;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;
import ms.model.Cell;

public class ActionHandler {
    private final MineField mineField;
    private final GridDimension dimensions;

    public ActionHandler(MineField mineField) {
        this.mineField = mineField;
        this.dimensions = mineField.getDimensions();
    }

    public boolean revealSingleCell(Position position) {

        Cell cell = mineField.getCell(position);
        if (cell.isRevealed() || cell.isFlagged()) {
            return false;
        }

        cell.reveal();
        return cell.isRevealed();
    }

    public int revealCascade(Position startPosition) {
        if (!CanCascadeRevealAt(startPosition)) {
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
            if (CanCascadeRevealAt(adjacent) && !mineField.getCell(adjacent).isRevealed()) {
                revealedCount += revealCascade(adjacent);
            }
        }

        return revealedCount;
    }

    private boolean CanCascadeRevealAt(Position position) {
        if (!dimensions.isValidPosition(position)) {
            return false;
        }

        Cell cell = mineField.getCell(position);
        return !cell.isRevealed() && !cell.isFlagged() && !cell.isMined();
    }
}
