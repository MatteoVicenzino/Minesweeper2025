package ms.logic.rules;

import ms.model.Position;

/**
 * The {@code GameRules} interface defines a contract for validating game operations
 * in a Minesweeper game. Implementations of this interface will provide specific
 * validation logic for different types of operations, such as revealing or flagging cells.
 */
@FunctionalInterface
public interface GameRules {
    void validate(Position position);
}
