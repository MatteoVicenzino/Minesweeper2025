package ms.view;

import static ms.view.AnsiColors.*;

/**
 * Utility class containing all static messages and formatting strings for the Minesweeper game.
 * <p>
 * This class provides pre-formatted, colored text messages for various game states,
 * commands, errors, and UI elements. All messages include ANSI color codes for
 * enhanced console display.
 * </p>
 * <p>
 * The class cannot be instantiated as it only contains static members.
 * </p>
 */
public final class Messages {

    /**
     * Private constructor to prevent instantiation of this utility class.
     * @throws UnsupportedOperationException if attempted to instantiate
     */
    private Messages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Welcome message displayed when starting the game.
     */
    public static final String WELCOME =
            "Welcome to the " + GREEN_BOLD + "Minesweeper!" + RESET + " Enter your commands:\n" +
                    "Type 'help' for available commands and game rules.";

    /**
     * Detailed help message explaining game commands and rules.
     */
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

    /**
     * Header for difficulty selection menu.
     */
    public static final String DIFFICULTY_SELECTION_HEADER =
            "\n" + GREEN_BOLD + "=== DIFFICULTY SELECTION ===" + RESET + "\n" +
                    "Choose your difficulty level:\n" +
                    "\n" +
                    "1. " + GREEN_BRIGHT + " EASY   (9x9 grid, 10 mines) " + RESET + "\n" +
                    "2. " + YELLOW_BRIGHT + " MEDIUM (16x16 grid, 40 mines) " + RESET + "\n" +
                    "3. " + RED_BRIGHT + " HARD   (16x30 grid, 99 mines) " + RESET + "\n" +
                    "\n" +
                    "Enter your choice (1-3):";

    /**
     * Message shown when invalid difficulty is selected.
     */
    public static final String DIFFICULTY_INVALID_CHOICE =
            RED + "Invalid choice. Please enter 1, 2, or 3." + RESET;

    /**
     * Header for game over state.
     */
    public static final String GAME_OVER_HEADER = "\n" + RED_BOLD + "--- GAME OVER ---" + RESET;

    /**
     * Header for current minefield display.
     */
    public static final String CURRENT_MINEFIELD_HEADER = "\n" + CYAN_BOLD + "--- Current Minefield ---" + RESET;

    /**
     * Header for game reset state.
     */
    public static final String GAME_RESET_HEADER = "\n" + GREEN_BOLD + "--- Game Reset ---" + RESET;

    /**
     * Victory message when player wins.
     */
    public static final String GAME_WON =
            GREEN_BOLD + "Congratulations! You revealed all non-mine cells! You Win!" + RESET;

    /**
     * Loss message when player hits a mine.
     */
    public static final String GAME_LOST =
            RED + "Boooom! You hit a mine! You Lose!" + RESET;

    /**
     * Success message after game reset.
     */
    public static final String GAME_RESET_SUCCESS =
            GREEN + "The game has been reset. Starting fresh!" + RESET;

    /**
     * Header for new game start.
     */
    public static final String NEW_GAME = "\n" + GREEN_BOLD + "--- Starting a new game ---" + RESET;

    /**
     * Header for new game with changed difficulty.
     */
    public static final String NEW_GAME_WITH_DIFFICULTY =
            "\n" + GREEN_BOLD + "--- Starting a new game with new difficulty ---" + RESET;

    /**
     * Confirmation message for easy difficulty selection.
     */
    public static final String SELECTED_EASY = GREEN + "Selected: EASY difficulty" + RESET;

    /**
     * Confirmation message for medium difficulty selection.
     */
    public static final String SELECTED_MEDIUM = YELLOW + "Selected: MEDIUM difficulty" + RESET;

    /**
     * Confirmation message for hard difficulty selection.
     */
    public static final String SELECTED_HARD = RED + "Selected: HARD difficulty" + RESET;

    /**
     * Creates a parsing error message with instructions.
     * @param message The error details
     * @return Formatted error message string
     */
    public static String parsingError(String message) {
        return RED_BOLD + "Error: " + message + "\n" + RESET +
                "Type '" + BLUE + "help" + RESET + "' for available commands.";
    }

    /**
     * Creates a game error message with instructions.
     * @param message The error details
     * @return Formatted error message string
     */
    public static String gameError(String message) {
        return RED_BOLD + "Game Error: " + message + "\n" + RESET +
                "Type '" + BLUE + "help" + RESET + "' for available commands.";
    }

    /**
     * Creates a coordinate error message with bounds information.
     * @param message The error details
     * @return Formatted error message string
     */
    public static String coordinateError(String message) {
        return RED_BOLD + "Error: Coordinates are out of bounds. " + message + "\n" + RESET +
                "Valid coordinates are 0-9 for both rows and columns.";
    }

    /**
     * Formats the revealed cell count statistics.
     * @param revealed Number of revealed cells
     * @param total Total non-mine cells
     * @return Formatted statistics string
     */
    public static String revealedCount(int revealed, int total) {
        return String.format("Revealed: %d / %d", revealed, total);
    }

    /**
     * Formats the flag count statistics.
     * @param flags Number of placed flags
     * @param totalMines Total mines in game
     * @return Formatted statistics string
     */
    public static String flagCount(int flags, int totalMines) {
        return String.format("Flags: %d / %d", flags, totalMines);
    }

    /**
     * Formats elapsed time for display.
     * @param timeSeconds Time in seconds
     * @return Formatted time string
     */
    public static String elapsedTime(long timeSeconds) {
        return String.format("Time: %ds", timeSeconds);
    }

    /**
     * Formats final game time for display.
     * @param timeSeconds Time in seconds
     * @return Formatted time string
     */
    public static String gameOverTime(long timeSeconds) {
        return String.format("Time taken: %d seconds.", timeSeconds);
    }

    /**
     * Thank you message when exiting the game.
     */
    public static final String THANK_YOU = "Thank you for playing!";

    /**
     * Goodbye message when exiting the game.
     */
    public static final String GOODBYE = "Exiting the game. Goodbye!";

    /**
     * Prompt for playing again after game over.
     */
    public static final String PLAY_AGAIN_PROMPT = "Play again? (yes/no/change): ";

    /**
     * Standard command prompt symbol.
     */
    public static final String COMMAND_PROMPT = "> ";

    /**
     * Separator line for visual organization.
     */
    public static final String GRID_SEPARATOR_LINE = "-------------------------";

    /**
     * Representation of a hidden cell.
     */
    public static final String CELL_HIDDEN = WHITE + " - " + RESET;

    /**
     * Representation of a flagged cell.
     */
    public static final String CELL_FLAGGED = RED_BOLD + " F " + RESET;

    /**
     * Representation of an exploded mine.
     */
    public static final String CELL_MINE_EXPLODED = RED_BOLD + " X " + RESET;

    /**
     * Representation of a revealed mine.
     */
    public static final String CELL_MINE_REVEALED = RED_BOLD + " * " + RESET;

    /**
     * Representation of an empty cell.
     */
    public static final String CELL_EMPTY = "   ";

    /**
     * Gets the colored display representation of a numbered cell.
     * @param count The adjacent mine count (1-8)
     * @return Formatted cell string with appropriate color
     */
    public static String getNumberedCell(int count) {
        return switch (count) {
            case 1 -> BLUE_BRIGHT + " 1 " + RESET;
            case 2 -> GREEN_BRIGHT + " 2 " + RESET;
            case 3 -> RED_BRIGHT + " 3 " + RESET;
            case 4 -> PURPLE_BRIGHT + " 4 " + RESET;
            case 5 -> YELLOW_BRIGHT + " 5 " + RESET;
            case 6 -> CYAN_BOLD + " 6 " + RESET;
            case 7 -> BLACK_BOLD + " 7 " + RESET;
            case 8 -> WHITE_BACKGROUND + BLACK + " 8 " + RESET;
            default -> " " + count + " ";
        };
    }
}