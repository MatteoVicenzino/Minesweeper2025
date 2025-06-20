package ms;
import ms.cell.Cell;

public class CellRevealHandler {
    private final MineField mineField;

    public CellRevealHandler(MineField mineField) {
        this.mineField = mineField;
    }

    public boolean revealSingleCell(Position position) {
        if (!mineField.isValid(position)) {
            return false;
        }

        Cell cell = mineField.getCell(position);
        if (cell.isRevealed() || cell.isFlagged()) {
            return false;
        }

        cell.reveal();
        return cell.isRevealed();
    }

    public int revealCascade(Position startPosition) {
        if (!canRevealAt(startPosition)) {
            return 0;
        }

        int revealedCount = 0;

        if (revealSingleCell(startPosition)) {
            revealedCount++;
        }

        if (mineField.countAdjacentMines(startPosition) > 0) {
            return revealedCount;
        }

        for (Position adjacent : getAdjacentPositions(startPosition)) {
            if (canRevealAt(adjacent) && !mineField.getCell(adjacent).isRevealed()) {
                revealedCount += revealCascade(adjacent);
            }
        }

        return revealedCount;
    }

    private boolean canRevealAt(Position position) {
        if (!mineField.isValid(position)) {
            return false;
        }

        Cell cell = mineField.getCell(position);
        return !cell.isRevealed() && !cell.isFlagged() && !cell.isMined();
    }

    private Position[] getAdjacentPositions(Position center) {
        Position[] adjacent = new Position[8];
        int index = 0;

        for (int r = center.row() - 1; r <= center.row() + 1; r++) {
            for (int c = center.col() - 1; c <= center.col() + 1; c++) {
                if (r != center.row() || c != center.col()) {
                    adjacent[index++] = new Position(r, c);
                }
            }
        }

        return adjacent;
    }
}
