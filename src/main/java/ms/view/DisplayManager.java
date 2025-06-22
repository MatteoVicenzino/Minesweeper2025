package ms.view;

import ms.logic.Game;
import ms.logic.GameStatus;
import ms.model.Cell;
import ms.model.MineField;
import ms.model.Position;

public class DisplayManager {

    public void displayHelp() {
        System.out.println("""
                
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
                ========================""");
    }

    public void displayGameStatus(Game game) {
        System.out.println("\n--- Current Minefield ---");

        if (isGameNotStarted(game)) {
            displayInitialGameView(game);
        } else {
            displayActiveGameView(game);
        }

        displayGameStatistics(game);
    }

    private void displayInitialGameView(Game game) {
        displayColumnHeader(game);
        displayGridSeparator(game);
        displayEmptyGrid(game);
    }

    private void displayActiveGameView(Game game) {
        displayColumnHeader(game);
        displayGridSeparator(game);
        displayGameGrid(game);
    }

    private boolean isGameNotStarted(Game game) {
        return game.getRevealed() == 0;
    }

    private void displayColumnHeader(Game game) {
        MineField mf = game.getMinefield();
        System.out.print("   ");
        for (int c = 0; c < mf.getWidth(); c++) {
            System.out.printf("%2d ", c);
        }
        System.out.println();
    }

    private void displayGridSeparator(Game game) {
        MineField mf = game.getMinefield();
        System.out.print("  +");
        for (int c = 0; c < mf.getWidth(); c++) {
            System.out.print("---");
        }
        System.out.println();
    }

    private void displayEmptyGrid(Game game) {
        MineField mf = game.getMinefield();
        for (int r = 0; r < mf.getHeight(); r++) {
            System.out.printf("%2d|", r);
            for (int c = 0; c < mf.getWidth(); c++) {
                System.out.print(" - ");
            }
            System.out.println();
        }
    }

    private void displayGameGrid(Game game) {
        MineField mf = game.getMinefield();
        for (int r = 0; r < mf.getHeight(); r++) {
            System.out.printf("%2d|", r);
            displayGridRow(game, mf, r);
            System.out.println();
        }
    }

    private void displayGridRow(Game game, MineField mf, int row) {
        for (int c = 0; c < mf.getWidth(); c++) {
            Position pos = new Position(row, c);
            String cellDisplay = getCellDisplayString(game, mf, pos);
            System.out.print(cellDisplay);
        }
    }

    private String getCellDisplayString(Game game, MineField mf, Position pos) {
        Cell cell = mf.getCell(pos);

        if (cell.isRevealed()) {
            return getRevealedCellDisplay(mf, pos, cell);
        }

        if (cell.isFlagged()) {
            return " F ";
        }

        if (shouldShowMine(game, cell)) {
            return " * ";
        }

        return " - ";
    }

    private boolean shouldShowMine(Game game, Cell cell) {
        return game.getGameOver() && cell.isMined() && !cell.isFlagged();
    }

    private String getRevealedCellDisplay(MineField mf, Position pos, Cell cell) {
        if (cell.isMined()) {
            return " X ";
        }

        int adjacentMines = mf.countAdjacentMines(pos);
        return " " + (adjacentMines == 0 ? " " : adjacentMines) + " ";
    }

    private void displayGameStatistics(Game game) {
        System.out.println("-------------------------");
        displayRevealedCount(game);
        displayFlagCount(game);
        displayElapsedTime(game);
        System.out.println("-------------------------");
    }

    private void displayRevealedCount(Game game) {
        int totalNonMineCells = game.getMinefield().getHeight() * game.getMinefield().getWidth() - game.getTotalMines();
        System.out.printf("Revealed: %d / %d%n", game.getRevealed(), totalNonMineCells);
    }

    private void displayFlagCount(Game game) {
        System.out.printf("Flags: %d / %d%n", game.getFlagsPlaced(), game.getTotalMines());
    }

    private void displayElapsedTime(Game game) {
        System.out.printf("Time: %ds%n", game.getElapsedTime() / 1000);
    }

    public void displayGameResult(Game game) {
        if (game.getGameStatus() == GameStatus.WON) {
            System.out.println("Congratulations! You revealed all non-mine cells! You Win!");
        } else if (game.getGameStatus() == GameStatus.LOST) {
            System.out.println("Boooom! You hit a mine! You Lose!");
        }
    }

    public void displayWelcome() {
        System.out.println("""
                Welcome to the Minesweeper CLI! Enter your commands:
                Type 'help' for available commands and game rules.""");
    }

    public void displayParsingError(String message) {
        System.out.printf("""
                Error: %s
                Type 'help' for available commands.%n""", message);
    }

    public void displayGameError(String message) {
        System.out.printf("""
                Game Error: %s
                Type 'help' for available commands.%n""", message);
    }

    public void displayCoordinateError(String message) {
        System.out.printf("""
                Error: Coordinates are out of bounds. %s
                Valid coordinates are 0-9 for both rows and columns.%n""", message);
    }

    public void displayGameOver(Game game) {
        System.out.println("\n--- GAME OVER ---");
        displayGameResult(game);
        System.out.printf("Time taken: %d seconds.%n", game.getElapsedTime() / 1000);
    }

    public void displayNewGame() {
        System.out.println("\n--- Starting a new game ---");
    }

    public void displayNewGameWithDifficulty() {
        System.out.println("\n--- Starting a new game with new difficulty ---");
    }

    public void displayGameReset() {
        System.out.println("\n--- Game Reset ---");
        System.out.println("The game has been reset. Starting fresh!");
    }

    public void displayThankYou() {
        System.out.println("Thank you for playing!");
    }

    public void displayGoodbye() {
        System.out.println("Exiting the game. Goodbye!");
    }
}