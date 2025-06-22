package ms.logic.rules;

import ms.logic.Game;
import ms.logic.status.GameStatusManager;
import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;

public class RevealRules implements GameRules {

    private final GridDimension dimensions;
    private final MineField minefield;
    private final GameStatusManager statusManager;

    public RevealRules(GridDimension dimensions, MineField minefield, GameStatusManager statusManager) {
        this.dimensions = dimensions;
        this.minefield = minefield;
        this.statusManager = statusManager;
    }

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