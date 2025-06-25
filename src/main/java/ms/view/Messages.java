package ms.view;

import static ms.view.AnsiColors.*;

public final class Messages {

    private Messages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final String WELCOME =
            "Welcome to the " + GREEN_BOLD + "Minesweeper!" + RESET + " Enter your commands:\n" +
                    "Type 'help' for available commands and game rules.";

    public static final String HELP =
            "\n" + CYAN_BOLD + "=== MINESWEEPER HELP ===" + RESET + "\n" +
                    YELLOW_BRIGHT + "Available commands:\n" + RESET +
                    "  " + CYAN + "reveal <row>,<col>" + RESET + "  - Reveal a cell at the specified coordinates\n" +
                    "  " + CYAN + "flag <row>,<col>" + RESET + "    - Toggle flag on a cell at the specified coordinates\n" +
                    "  " + CYAN + "help" + RESET + "                - Show this help message\n" +
                    "  " + CYAN + "reset" + RESET + "               - Reset the current game\n" +
                    "  " + CYAN + "quit" + RESET + "                - Exit the game\n" +
                    "\n" + YELLOW_BRIGHT + "Examples:" + RESET + "\n" +
                    "  " + CYAN + "reveal 3,4" + RESET + "          - Reveals the cell at row 3, column 4\n" +
                    "  " + CYAN + "flag 0,0" + RESET + "            - Toggles flag on cell at row 0, column 0\n" +
                    "\n" + YELLOW_BRIGHT + "Game Rules:" + RESET + "\n" +
                    "  - The goal is to reveal all cells that don't contain mines\n" +
                    "  - Numbers show how many mines are adjacent to that cell\n" +
                    "  - Use flags to mark cells you think contain mines\n" +
                    "  - If you reveal a mine, you lose!\n" +
                    "  - Your first reveal is always safe\n" +
                    "\n" + YELLOW_BRIGHT + "Field Symbols:" + RESET + "\n" +
                    "  -  = Covered cell\n" +
                    "  " + RED + "F" + RESET + "  = Flagged cell\n" +
                    "  1-8 = Number of adjacent mines\n" +
                    "  (space) = Empty cell (no adjacent mines)\n" +
                    "  " + RED_BOLD + "X" + RESET + "  = Exploded mine\n" +
                    "  " + RED + "*" + RESET + "  = Mine (shown only when game ends)\n" +
                    CYAN_BOLD + "========================" + RESET;

    public static final String DIFFICULTY_SELECTION_HEADER =
            "\n" + GREEN_BOLD + "=== DIFFICULTY SELECTION ===" + RESET + "\n" +
                    "Choose your difficulty level:\n" +
                    "\n" +
                    "1. " + GREEN_BACKGROUND + BLACK + " EASY   (9x9 grid, 10 mines) " + RESET + "\n" +
                    "2. " + YELLOW_BACKGROUND + BLACK + " MEDIUM (16x16 grid, 40 mines) " + RESET + "\n" +
                    "3. " + RED_BACKGROUND + BLACK + " HARD   (16x30 grid, 99 mines) " + RESET + "\n" +
                    "\n" +
                    "Enter your choice (1-3):";

    public static final String DIFFICULTY_INVALID_CHOICE =
            RED + "Invalid choice. Please enter 1, 2, or 3." + RESET;

    public static final String GAME_OVER_HEADER = "\n" + RED_BOLD + "--- GAME OVER ---" + RESET;
    public static final String CURRENT_MINEFIELD_HEADER = "\n" + CYAN_BOLD + "--- Current Minefield ---" + RESET;
    public static final String GAME_RESET_HEADER = "\n" + GREEN_BOLD + "--- Game Reset ---" + RESET;

    public static final String GAME_WON =
            GREEN_BOLD + "Congratulations! You revealed all non-mine cells! You Win!" + RESET;

    public static final String GAME_LOST =
            RED + "Boooom! You hit a mine! You Lose!" + RESET;

    public static final String GAME_RESET_SUCCESS =
            GREEN + "The game has been reset. Starting fresh!" + RESET;

    public static final String NEW_GAME = "\n" + GREEN_BOLD + "--- Starting a new game ---" + RESET;

    public static final String NEW_GAME_WITH_DIFFICULTY =
            "\n" + GREEN_BOLD + "--- Starting a new game with new difficulty ---" + RESET;

    public static final String SELECTED_EASY = GREEN + "Selected: EASY difficulty" + RESET;
    public static final String SELECTED_MEDIUM = YELLOW + "Selected: MEDIUM difficulty" + RESET;
    public static final String SELECTED_HARD = RED + "Selected: HARD difficulty" + RESET;

    public static String parsingError(String message) {
        return RED_BOLD + "Error: " + message + "\n" + RESET +
                "Type '" + BLUE + "help" + RESET + "' for available commands.";
    }

    public static String gameError(String message) {
        return RED_BOLD + "Game Error: " + message + "\n" + RESET +
                "Type '" + BLUE + "help" + RESET + "' for available commands.";
    }

    public static String coordinateError(String message) {
        return RED_BOLD + "Error: Coordinates are out of bounds. " + message + "\n" + RESET +
                "Valid coordinates are 0-9 for both rows and columns.";
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