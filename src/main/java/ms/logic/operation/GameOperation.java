package ms.logic.operation;

import ms.model.Position;

/**
 * The {@code GameOperation} interface defines a contract for operations that can be performed
 * on a Minesweeper game, such as revealing or flagging cells.
 * Each operation is executed on a specific position in the game grid.
 */
@FunctionalInterface
public interface GameOperation {

    /**
     * Executes the operation on the specified position.
     *
     * @param position The position of the cell to be operated on.
     */
    void execute(Position position);
}
