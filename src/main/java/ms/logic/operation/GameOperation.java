package ms.logic.operation;

import ms.model.Position;

@FunctionalInterface
public interface GameOperation {
    void execute(Position position);
}
