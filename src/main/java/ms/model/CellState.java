package ms.model;

/**
 * The {@code CellState} enum represents the visual state of cell,
 * provides state transitions for flagging and revealing operations.
 */
public enum CellState {
    HIDDEN {
        @Override
        public CellState toggleFlag() {
            return FLAGGED;
        }

        @Override
        public CellState reveal(boolean isMined) {
            return isMined ? EXPLODED : REVEALED;
        }
    },

    FLAGGED {
        @Override
        public CellState toggleFlag() {
            return HIDDEN;
        }

        @Override
        public CellState reveal(boolean isMined) {
            return this;
        }
    },

    REVEALED {
        @Override
        public CellState toggleFlag() {
            return this;
        }

        @Override
        public CellState reveal(boolean isMined) {
            return this;
        }
    },

    EXPLODED {
        @Override
        public CellState toggleFlag() {
            return this;
        }

        @Override
        public CellState reveal(boolean isMined) {
            return this;
        }
    };

    /**
     * Toggles the flag state of this cell.
     */
    public abstract CellState toggleFlag();

    /**
     * Reveals this cell, changing its state based on whether it contains a mine.
     */
    public abstract CellState reveal(boolean isMined);
}
