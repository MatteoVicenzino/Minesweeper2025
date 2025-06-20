package ms.Cell;

public enum CellState {
    HIDDEN {
        @Override
        public CellState toggleFlag() { return FLAGGED; }

        @Override
        public CellState reveal(boolean isMined) {
            return isMined ? EXPLODED : REVEALED;
        }
    },

    FLAGGED {
        @Override
        public CellState toggleFlag() { return HIDDEN; }

        @Override
        public CellState reveal(boolean isMined) { return this; }
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
        public CellState toggleFlag() { return this; }

        @Override
        public CellState reveal(boolean isMined) { return this; }
    };

    public abstract CellState toggleFlag();
    public abstract CellState reveal(boolean isMined);
}
