package ms.logic.operation;

import ms.logic.status.GameStatistics;
import ms.model.Cell;
import ms.model.MineField;
import ms.model.Position;

/**
 * The {@code FlagOperation} class implements the {@code GameOperation} interface
 * to handle flagging and unflagging cells in a Minesweeper game.
 */
public class FlagOperation implements GameOperation {

    private final MineField minefield;
    private final GameStatistics stats;

    /**
     * Constructor for FlagOperation.
     * @param minefield The minefield containing the cells to be flagged or unflagged.
     * @param stats The game statistics to track flagged cells.
     */
    public FlagOperation(MineField minefield, GameStatistics stats) {
        this.minefield = minefield;
        this.stats = stats;
    }

    /**
     * Executes the flagging operation on the specified position.
     * Toggles the flag state of the cell at the given position and updates the statistics accordingly.
     *
     * @param position The position of the cell to be flagged or unflagged.
     */
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