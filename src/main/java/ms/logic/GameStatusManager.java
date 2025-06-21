package ms.logic;


public class GameStatusManager {
    private GameStatus currentStatus;

    public GameStatusManager() {
        this.currentStatus = GameStatus.NOT_STARTED;
    }

    public void startGame() {
        if (currentStatus == GameStatus.NOT_STARTED) {
            currentStatus = GameStatus.IN_PROGRESS;
        }
    }

    public void endGameWithWin() {
        if (currentStatus == GameStatus.IN_PROGRESS) {
            currentStatus = GameStatus.WON;
        }
    }

    public void endGameWithLoss() {
        if (currentStatus == GameStatus.IN_PROGRESS) {
            currentStatus = GameStatus.LOST;
        }
    }

    public void resetGame() {
        currentStatus = GameStatus.NOT_STARTED;
    }

    public GameStatus getCurrentStatus() {
        return currentStatus;
    }

    public boolean isGameOver() {
        return currentStatus == GameStatus.WON || currentStatus == GameStatus.LOST;
    }

    public boolean isInProgress() {
        return currentStatus == GameStatus.IN_PROGRESS;
    }

    public boolean canPlay() {
        return currentStatus == GameStatus.IN_PROGRESS || currentStatus == GameStatus.NOT_STARTED;
    }
}