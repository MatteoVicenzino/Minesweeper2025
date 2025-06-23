package ms.logic.operation;

import ms.logic.status.GameStatistics;
import ms.model.Cell;
import ms.model.MineField;
import ms.model.Position;

public class FlagOperation implements GameOperation {

    private final MineField minefield;
    private final GameStatistics stats;

    public FlagOperation(MineField minefield, GameStatistics stats) {
        this.minefield = minefield;
        this.stats = stats;
    }

    @Override
    public void execute(Position position) {
        Cell cell = minefield.getCell(position);

        if (cell.isFlagged()) {
            cell.toggleFlag();
            stats.decrementFlags();
        } else {
            cell.toggleFlag();
            stats.incrementFlags();
        }
    }
}