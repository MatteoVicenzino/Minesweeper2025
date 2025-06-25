package ms.view;

import ms.logic.Game;
import ms.logic.status.GameStatus;
import ms.model.Cell;
import ms.model.MineField;
import ms.model.Position;

public class DisplayManager {

    public void displayHelp() {
        System.out.println(Messages.HELP);
    }

    public void displayGameStatus(Game game) {
        System.out.println(Messages.CURRENT_MINEFIELD_HEADER);

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
        MineField minefield = game.getMinefield();
        System.out.print("   ");
        for (int c = 0; c < minefield.getWidth(); c++) {
            System.out.printf("%2d ", c);
        }
        System.out.println();
    }

    private void displayGridSeparator(Game game) {
        MineField minefield = game.getMinefield();
        System.out.print("  +");
        for (int c = 0; c < minefield.getWidth(); c++) {
            System.out.print("---");
        }
        System.out.println();
    }

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

    private void displayGameGrid(Game game) {
        MineField minefield = game.getMinefield();
        for (int r = 0; r < minefield.getHeight(); r++) {
            System.out.printf("%2d|", r);
            displayGridRow(game, minefield, r);
            System.out.println();
        }
    }

    private void displayGridRow(Game game, MineField mineField, int row) {
        for (int c = 0; c < mineField.getWidth(); c++) {
            Position position = new Position(row, c);
            String cellDisplay = getCellDisplayString(game, mineField, position);
            System.out.print(cellDisplay);
        }
    }

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

    private boolean shouldShowMine(Game game, Cell cell) {
        return game.isGameOver() && cell.isMined() && !cell.isFlagged();
    }

    private String getRevealedCellDisplay(MineField mineField, Position position, Cell cell) {
        if (cell.isMined()) {
            return Messages.CELL_MINE_EXPLODED;
        }

        int adjacentMines = mineField.countAdjacentMines(position);
        return adjacentMines == 0 ? Messages.CELL_EMPTY : Messages.getNumberedCell(adjacentMines);
    }

    private void displayGameStatistics(Game game) {
        System.out.println(Messages.GRID_SEPARATOR_LINE);
        displayRevealedCount(game);
        displayFlagCount(game);
        displayElapsedTime(game);
        System.out.println(Messages.GRID_SEPARATOR_LINE);
    }

    private void displayRevealedCount(Game game) {
        System.out.println(Messages.revealedCount(game.getRevealed(), game.getTotalOfNonMineCells()));
    }

    private void displayFlagCount(Game game) {
        System.out.println(Messages.flagCount(game.getFlagsPlaced(), game.getTotalMines()));
    }

    private void displayElapsedTime(Game game) {
        System.out.println(Messages.elapsedTime(game.getElapsedTime() / 1000));
    }

    public void displayGameResult(Game game) {
        if (game.getGameStatus() == GameStatus.WON) {
            System.out.println(Messages.GAME_WON);
        } else if (game.getGameStatus() == GameStatus.LOST) {
            System.out.println(Messages.GAME_LOST);
        }
    }

    public void displayWelcome() {
        System.out.println(Messages.WELCOME);
    }

    public void displayParsingError(String message) {
        System.out.println(Messages.parsingError(message));
    }

    public void displayGameError(String message) {
        System.out.println(Messages.gameError(message));
    }

    public void displayCoordinateError(String message) {
        System.out.println(Messages.coordinateError(message));
    }

    public void displayGameOver(Game game) {
        System.out.println(Messages.GAME_OVER_HEADER);
        displayGameResult(game);
        System.out.println(Messages.gameOverTime(game.getElapsedTime() / 1000));
    }

    public void displayNewGame() {
        System.out.println(Messages.NEW_GAME);
    }

    public void displayNewGameWithDifficulty() {
        System.out.println(Messages.NEW_GAME_WITH_DIFFICULTY);
    }

    public void displayGameReset() {
        System.out.println(Messages.GAME_RESET_HEADER);
        System.out.println(Messages.GAME_RESET_SUCCESS);
    }

    public void displayThankYou() {
        System.out.println(Messages.THANK_YOU);
    }

    public void displayGoodbye() {
        System.out.println(Messages.GOODBYE);
    }
}