package ms.logic.status;

/**
 * Manages and controls the state transitions of a Minesweeper game.
 * This class provides methods to start, end, and reset the game,
 * while maintaining the current game status.
 */
public class GameStatusManager {
    /** The current status of the game. */
    private GameStatus currentStatus;

    /**
     * Constructs a new GameStatusManager with the initial game state set to NOT_STARTED.
     */
    public GameStatusManager() {
        this.currentStatus = GameStatus.NOT_STARTED;
    }

    /**
     * Transitions the game from NOT_STARTED to IN_PROGRESS status.
     * Only works if the current status is NOT_STARTED.
     */
    public void startGame() {
        if (currentStatus == GameStatus.NOT_STARTED) {
            currentStatus = GameStatus.IN_PROGRESS;
        }
    }

    /**
     * Ends the game with the specified status.
     * Only works if the current status is IN_PROGRESS.
     *
     * @param status the final game status (either WON or LOST)
     * @throws IllegalArgumentException if status is not WON or LOST
     */
    public void endGame(GameStatus status) {
        if (currentStatus == GameStatus.IN_PROGRESS) {
            if (status != GameStatus.WON && status != GameStatus.LOST) {
                throw new IllegalArgumentException("End game status must be either WON or LOST");
            }
            currentStatus = status;
        }
    }

    /**
     * Resets the game status to NOT_STARTED.
     * This can be called regardless of the current game state.
     */
    public void resetGame() {
        currentStatus = GameStatus.NOT_STARTED;
    }

    /**
     * Gets the current game status.
     *
     * @return the current GameStatus
     */
    public GameStatus getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Checks if the game is in a terminal state (either WON or LOST).
     *
     * @return true if the game is over (WON or LOST), false otherwise
     */
    public boolean isGameOver() {
        return currentStatus == GameStatus.WON || currentStatus == GameStatus.LOST;
    }
}