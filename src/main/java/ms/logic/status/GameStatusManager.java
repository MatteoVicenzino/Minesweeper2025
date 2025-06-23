package ms.logic.status;


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

    public void endGame(GameStatus status) {
        if (currentStatus == GameStatus.IN_PROGRESS) {
            currentStatus = status;
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
}