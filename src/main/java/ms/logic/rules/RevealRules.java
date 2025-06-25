package ms.logic.rules;

import ms.logic.Game;
import ms.logic.status.GameStatusManager;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;

/**
 * The {@code RevealRules} class implements the {@code GameRules} interface
 * to validate reveal operations in a Minesweeper game.
 * It checks if the position is valid, if the game is over, and if the cell is flagged.
 */
public class RevealRules implements GameRules {

    private final GridDimension dimensions;
    private final MineField minefield;
    private final GameStatusManager statusManager;

    /**
     * Constructor for RevealRules.
     *
     * @param dimensions The dimensions of the grid.
     * @param minefield The minefield containing the cells to be revealed.
     * @param statusManager The manager for handling game status changes.
     */
    public RevealRules(GridDimension dimensions, MineField minefield, GameStatusManager statusManager) {
        this.dimensions = dimensions;
        this.minefield = minefield;
        this.statusManager = statusManager;
    }

    /**
     * Validates the reveal operation on the specified position.
     * It checks if the position is within bounds, if the game is over,
     * and if the cell is flagged.
     *
     * @param position The position of the cell to be revealed.
     * @throws Game.InvalidGameOperationException if the operation is invalid.
     */
    @Override
    public void validate(Position position) {
        dimensions.validatePosition(position);

        if (statusManager.isGameOver()) {
            throw new Game.InvalidGameOperationException("Cannot reveal cells after game is over!");
        }

        if (minefield.getCell(position).isFlagged()) {
            throw new Game.InvalidGameOperationException("Cannot reveal a flagged cell! Remove flag first.");
        }
    }
}