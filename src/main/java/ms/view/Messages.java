package ms.view;

public final class Messages {

    private Messages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final String WELCOME = """
            Welcome to the Minesweeper CLI! Enter your commands:
            Type 'help' for available commands and game rules.""";

    public static final String HELP = """
            
            === MINESWEEPER HELP ===
            Available commands:
              reveal <row>,<col>  - Reveal a cell at the specified coordinates
              flag <row>,<col>    - Toggle flag on a cell at the specified coordinates
              help                - Show this help message
              reset               - Reset the current game
              quit                - Exit the game
            
            Examples:
              reveal 3,4          - Reveals the cell at row 3, column 4
              flag 0,0            - Toggles flag on cell at row 0, column 0
            
            Game Rules:
              • The goal is to reveal all cells that don't contain mines
              • Numbers show how many mines are adjacent to that cell
              • Use flags to mark cells you think contain mines
              • If you reveal a mine, you lose!
              • Your first reveal is always safe
            
            Field Symbols:
              -  = Covered cell
              F  = Flagged cell
              1-8= Number of adjacent mines
              (space) = Empty cell (no adjacent mines)
              X  = Exploded mine
              *  = Mine (shown only when game ends)
            ========================""";

    public static final String DIFFICULTY_SELECTION_HEADER = """
            
            === DIFFICULTY SELECTION ===
            Choose your difficulty level:
            
            1. EASY   (9x9 grid, 10 mines)
            2. MEDIUM (16x16 grid, 40 mines)
            3. HARD   (16x30 grid, 99 mines)
            
            Enter your choice (1-3):""";

    public static final String DIFFICULTY_INVALID_CHOICE =
            "Invalid choice. Please enter 1, 2, or 3.";


    public static final String GAME_OVER_HEADER = "\n--- GAME OVER ---";
    public static final String CURRENT_MINEFIELD_HEADER = "\n--- Current Minefield ---";
    public static final String GAME_RESET_HEADER = "\n--- Game Reset ---";

    public static final String GAME_WON =
            "Congratulations! You revealed all non-mine cells! You Win!";

    public static final String GAME_LOST =
            "Boooom! You hit a mine! You Lose!";

    public static final String GAME_RESET_SUCCESS =
            "The game has been reset. Starting fresh!";

    public static final String NEW_GAME = "\n--- Starting a new game ---";

    public static final String NEW_GAME_WITH_DIFFICULTY =
            "\n--- Starting a new game with new difficulty ---";

    public static final String SELECTED_EASY = "Selected: EASY difficulty";
    public static final String SELECTED_MEDIUM = "Selected: MEDIUM difficulty";
    public static final String SELECTED_HARD = "Selected: HARD difficulty";

    public static String parsingError(String message) {
        return String.format("""
                Error: %s
                Type 'help' for available commands.""", message);
    }

    public static String gameError(String message) {
        return String.format("""
                Game Error: %s
                Type 'help' for available commands.""", message);
    }

    public static String coordinateError(String message) {
        return String.format("""
                Error: Coordinates are out of bounds. %s
                Valid coordinates are 0-9 for both rows and columns.""", message);
    }

    public static String revealedCount(int revealed, int total) {
        return String.format("Revealed: %d / %d", revealed, total);
    }

    public static String flagCount(int flags, int totalMines) {
        return String.format("Flags: %d / %d", flags, totalMines);
    }

    public static String elapsedTime(long timeSeconds) {
        return String.format("Time: %ds", timeSeconds);
    }

    public static String gameOverTime(long timeSeconds) {
        return String.format("Time taken: %d seconds.", timeSeconds);
    }

    public static final String THANK_YOU = "Thank you for playing!";
    public static final String GOODBYE = "Exiting the game. Goodbye!";

    public static final String PLAY_AGAIN_PROMPT = "Play again? (yes/no/change): ";
    public static final String COMMAND_PROMPT = "> ";

    public static final String GRID_SEPARATOR_LINE = "-------------------------";

    public static final String CELL_HIDDEN = " - ";
    public static final String CELL_FLAGGED = " F ";
    public static final String CELL_MINE_EXPLODED = " X ";
    public static final String CELL_MINE_REVEALED = " * ";
    public static final String CELL_EMPTY = "   ";
}