package ms.logic.rules;

import ms.logic.Game;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;

public class FlagRules implements GameRules {

    private final GridDimension dimensions;
    private final MineField minefield;
    private final boolean firstReveal;

    public FlagRules(GridDimension dimensions, MineField minefield, boolean firstReveal) {
        this.dimensions = dimensions;
        this.minefield = minefield;
        this.firstReveal = firstReveal;
    }

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