package ms.logic.rules;

import ms.logic.Game;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;

/**
 * The {@code FlagRules} class implements the {@code GameRules} interface
 * to validate flagging operations in a Minesweeper game.
 * It checks if the position is valid, if the first move is a flag, and if the cell is already revealed.
 */
public class FlagRules implements GameRules {

    private final GridDimension dimensions;
    private final MineField minefield;
    private final boolean firstReveal;

    /**
     * Constructor for FlagRules.
     *
     * @param dimensions The dimensions of the grid.
     * @param minefield The minefield containing the cells to be flagged.
     * @param firstReveal Indicates if this is the first reveal in the game.
     */
    public FlagRules(GridDimension dimensions, MineField minefield, boolean firstReveal) {
        this.dimensions = dimensions;
        this.minefield = minefield;
        this.firstReveal = firstReveal;
    }

    /**
     * Validates the flagging operation on the specified position.
     * It checks if the position is within bounds, if the first move is a flag,
     * and if the cell is already revealed.
     *
     * @param position The position of the cell to be flagged.
     * @throws Game.InvalidGameOperationException if the operation is invalid.
     */
    @Override
    public void validate(Position position) {
        dimensions.validatePosition(position);

        if (firstReveal) {
            throw new Game.InvalidGameOperationException("Flagging is not a valid first move!");
        }

        if (minefield.getCell(position).isRevealed()) {
            throw new Game.InvalidGameOperationException("Cannot flag a revealed cell!");
        }
    }
}