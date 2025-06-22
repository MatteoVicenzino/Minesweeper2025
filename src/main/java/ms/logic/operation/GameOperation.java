package ms.logic.operation;

import ms.model.Position;

@FunctionalInterface
public interface GameOperation {
    int execute(Position position);
}
