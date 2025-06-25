package ms.logic.status;

/**
 * Represents the possible states of a Minesweeper game.
 * This enum is used to track the current status of game progression.
 */
public enum GameStatus {
    /**
     * The game has been created but not yet started by the player.
     */
    NOT_STARTED,

    /**
     * The game is currently being played.
     */
    IN_PROGRESS,

    /**
     * The player has successfully revealed all non-mine cells.
     */
    WON,

    /**
     * The player has revealed a cell containing a mine.
     */
    LOST
}