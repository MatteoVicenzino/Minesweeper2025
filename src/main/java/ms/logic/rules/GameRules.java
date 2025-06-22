package ms.logic.rules;

import ms.model.Position;

@FunctionalInterface
public interface GameRules {
    void validate(Position postition);
}
