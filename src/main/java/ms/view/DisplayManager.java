package ms.view;

import ms.logic.Game;
import ms.logic.status.GameStatus;
import ms.model.Cell;
import ms.model.MineField;
import ms.model.Position;

/**
 * Manages the display output for the Minesweeper game.
 * Handles rendering the game state, messages, and errors.
 */
public class DisplayManager {

    /**
     * Displays the help message with game instructions and commands.
     */
    public void displayHelp() {
        System.out.println(Messages.HELP);
    }

    /**
     * Displays the current game status, including the minefield and statistics.
     *
     * @param game The Game instance to display.
     */
    public void displayGameStatus(Game game) {
        System.out.println(Messages.CURRENT_MINEFIELD_HEADER);

        if (isGameNotStarted(game)) {
            displayInitialGameView(game);
        } else {
            displayActiveGameView(game);
        }

        displayGameStatistics(game);
    }

    /**
     * Displays the initial game view with all cells hidden.
     *
     * @param game The Game instance to display.
     */
    private void displayInitialGameView(Game game) {
        displayColumnHeader(game);
        displayGridSeparator(game);
        displayEmptyGrid(game);
    }

    /**
     * Displays the active game view with revealed and flagged cells.
     *
     * @param game The Game instance to display.
     */
    private void displayActiveGameView(Game game) {
        displayColumnHeader(game);
        displayGridSeparator(game);
        displayGameGrid(game);
    }

    /**
     * Checks if the game has not started (no cells revealed).
     *
     * @param game The Game instance to check.
     * @return True if no cells have been revealed, false otherwise.
     */
    private boolean isGameNotStarted(Game game) {
        return game.getRevealed() == 0;
    }

    /**
     * Displays the column headers for the minefield grid.
     *
     * @param game The Game instance to display.
     */
    private void displayColumnHeader(Game game) {
        MineField minefield = game.getMinefield();
        System.out.print("   ");
        for (int c = 0; c < minefield.getWidth(); c++) {
            System.out.printf("%2d ", c);
        }
        System.out.println();
    }

    /**
     * Displays the separator line for the minefield grid.
     *
     * @param game The Game instance to display.
     */
    private void displayGridSeparator(Game game) {
        MineField minefield = game.getMinefield();
        System.out.print("  +");
        for (int c = 0; c < minefield.getWidth(); c++) {
            System.out.print("---");
        }
        System.out.println();
    }

    /**
     * Displays an empty grid (all cells hidden).
     *
     * @param game The Game instance to display.
     */
    private void displayEmptyGrid(Game game) {
        MineField minefield = game.getMinefield();
        for (int r = 0; r < minefield.getHeight(); r++) {
            System.out.printf("%2d|", r);
            for (int c = 0; c < minefield.getWidth(); c++) {
                System.out.print(Messages.CELL_HIDDEN);
            }
            System.out.println();
        }
    }

    /**
     * Displays the game grid with revealed and flagged cells.
     *
     * @param game The Game instance to display.
     */
    private void displayGameGrid(Game game) {
        MineField minefield = game.getMinefield();
        for (int r = 0; r < minefield.getHeight(); r++) {
            System.out.printf("%2d|", r);
            displayGridRow(game, minefield, r);
            System.out.println();
        }
    }

    /**
     * Displays a single row of the minefield grid.
     *
     * @param game      The Game instance to display.
     * @param mineField The MineField instance to display.
     * @param row       The row number to display.
     */
    private void displayGridRow(Game game, MineField mineField, int row) {
        for (int c = 0; c < mineField.getWidth(); c++) {
            Position position = new Position(row, c);
            String cellDisplay = getCellDisplayString(game, mineField, position);
            System.out.print(cellDisplay);
        }
    }

    /**
     * Generates the display string for a specific cell.
     *
     * @param game      The Game instance to display.
     * @param mineField The MineField instance to display.
     * @param position  The Position of the cell to display.
     * @return The formatted string representing the cell.
     */
    private String getCellDisplayString(Game game, MineField mineField, Position position) {
        Cell cell = mineField.getCell(position);

        if (cell.isRevealed()) {
            return getRevealedCellDisplay(mineField, position, cell);
        }

        if (cell.isFlagged()) {
            return Messages.CELL_FLAGGED;
        }

        if (shouldShowMine(game, cell)) {
            return Messages.CELL_MINE_REVEALED;
        }

        return Messages.CELL_HIDDEN;
    }

    /**
     * Checks if a mine should be shown (game over and cell is mined).
     *
     * @param game The Game instance to check.
     * @param cell The Cell to check.
     * @return True if the mine should be shown, false otherwise.
     */
    private boolean shouldShowMine(Game game, Cell cell) {
        return game.isGameOver() && cell.isMined() && !cell.isFlagged();
    }

    /**
     * Generates the display string for a revealed cell.
     *
     * @param mineField The MineField instance to display.
     * @param position  The Position of the cell to display.
     * @param cell      The Cell to display.
     * @return The formatted string representing the revealed cell.
     */
    private String getRevealedCellDisplay(MineField mineField, Position position, Cell cell) {
        if (cell.isMined()) {
            return Messages.CELL_MINE_EXPLODED;
        }

        int adjacentMines = mineField.countAdjacentMines(position);
        return adjacentMines == 0 ? Messages.CELL_EMPTY : Messages.getNumberedCell(adjacentMines);
    }

    /**
     * Displays game statistics (revealed cells, flags, elapsed time).
     *
     * @param game The Game instance to display.
     */
    private void displayGameStatistics(Game game) {
        System.out.println(Messages.GRID_SEPARATOR_LINE);
        displayRevealedCount(game);
        displayFlagCount(game);
        displayElapsedTime(game);
        System.out.println(Messages.GRID_SEPARATOR_LINE);
    }

    /**
     * Displays the count of revealed cells.
     *
     * @param game The Game instance to display.
     */
    private void displayRevealedCount(Game game) {
        System.out.println(Messages.revealedCount(game.getRevealed(), game.getTotalOfNonMineCells()));
    }

    /**
     * Displays the count of placed flags.
     *
     * @param game The Game instance to display.
     */
    private void displayFlagCount(Game game) {
        System.out.println(Messages.flagCount(game.getFlagsPlaced(), game.getTotalMines()));
    }

    /**
     * Displays the elapsed game time.
     *
     * @param game The Game instance to display.
     */
    private void displayElapsedTime(Game game) {
        System.out.println(Messages.elapsedTime(game.getElapsedTime() / 1000));
    }

    /**
     * Displays the game result (won/lost).
     *
     * @param game The Game instance to display.
     */
    public void displayGameResult(Game game) {
        if (game.getGameStatus() == GameStatus.WON) {
            System.out.println(Messages.GAME_WON);
        } else if (game.getGameStatus() == GameStatus.LOST) {
            System.out.println(Messages.GAME_LOST);
        }
    }

    /**
     * Displays the welcome message.
     */
    public void displayWelcome() {
        System.out.println(Messages.WELCOME);
    }

    /**
     * Displays a parsing error message.
     *
     * @param message The error message to display.
     */
    public void displayParsingError(String message) {
        System.out.println(Messages.parsingError(message));
    }

    /**
     * Displays a game error message.
     *
     * @param message The error message to display.
     */
    public void displayGameError(String message) {
        System.out.println(Messages.gameError(message));
    }

    /**
     * Displays a coordinate error message.
     *
     * @param message The error message to display.
     */
    public void displayCoordinateError(String message) {
        System.out.println(Messages.coordinateError(message));
    }

    /**
     * Displays the game over message and result.
     *
     * @param game The Game instance to display.
     */
    public void displayGameOver(Game game) {
        System.out.println(Messages.GAME_OVER_HEADER);
        displayGameResult(game);
        System.out.println(Messages.gameOverTime(game.getElapsedTime() / 1000));
    }

    /**
     * Displays the new game message.
     */
    public void displayNewGame() {
        System.out.println(Messages.NEW_GAME);
    }

    /**
     * Displays the new game with difficulty message.
     */
    public void displayNewGameWithDifficulty() {
        System.out.println(Messages.NEW_GAME_WITH_DIFFICULTY);
    }

    /**
     * Displays the game reset message.
     */
    public void displayGameReset() {
        System.out.println(Messages.GAME_RESET_HEADER);
        System.out.println(Messages.GAME_RESET_SUCCESS);
    }

    /**
     * Displays the thank you message.
     */
    public void displayThankYou() {
        System.out.println(Messages.THANK_YOU);
    }

    /**
     * Displays the goodbye message.
     */
    public void displayGoodbye() {
        System.out.println(Messages.GOODBYE);
    }
}