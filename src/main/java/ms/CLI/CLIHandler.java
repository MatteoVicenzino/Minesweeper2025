package ms.CLI;
import ms.*;

public class CLIHandler {
    private final InputManager inputManager;
    private final DisplayManager displayManager;
    private Game game;
    private boolean shouldContinue = true;

    public CLIHandler(CommandParser parser, Game game) {
        this.inputManager = new InputManager(parser);
        this.displayManager = new DisplayManager();
        this.game = game;
    }

    public CLIHandler(CommandParser parser) {
        this.inputManager = new InputManager(parser);
        this.displayManager = new DisplayManager();
        this.game = null;
    }

    public Command handleInput(String input) {
        return inputManager.handleInput(input);
    }

    public void setScanner(java.util.Scanner scanner) {
        inputManager.setScanner(scanner);
    }

    public void start() {
        displayManager.displayWelcome();

        if (game == null) {
            Difficulty selectedDifficulty = inputManager.selectDifficulty();
            this.game = new Game(selectedDifficulty);
        }

        displayManager.displayGameStatus(game);

        while (inputManager.hasNextLine() && shouldContinue) {
            String inputLine = inputManager.readInputLine();

            try {
                Command command = handleInput(inputLine);

                switch (command.getType()) {
                    case REVEAL:
                        Position revealPos = new Position(command.getRow(), command.getCol());
                        game.revealCell(revealPos);
                        break;
                    case FLAG:
                        Position flagPos = new Position(command.getRow(), command.getCol());
                        game.flagCell(flagPos);
                        break;
                    case HELP:
                        displayManager.displayHelp();
                        continue;
                    case QUIT:
                        displayManager.displayGoodbye();
                        return;
                }

                displayManager.displayGameStatus(game);

                if (game.getGameOver()) {
                    handleGameOver();
                }

            } catch (IllegalArgumentException e) {
                displayManager.displayParsingError(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                displayManager.displayCoordinateError(e.getMessage());
            }
        }
    }

    private void handleGameOver() {
        displayManager.displayGameOver(game);
        handlePlayAgainChoice();
    }

    private void handlePlayAgainChoice() {
        String playAgainInput = inputManager.readPlayAgainChoice();

        if (playAgainInput.equals("yes")) {
            game.resetGame();
            displayManager.displayNewGame();
            displayManager.displayGameStatus(game);
        } else if (playAgainInput.equals("change")) {
            Difficulty newDifficulty = inputManager.selectDifficulty();
            this.game = new Game(newDifficulty);
            displayManager.displayNewGameWithDifficulty();
            displayManager.displayGameStatus(game);
        } else {
            displayManager.displayThankYou();
            shouldContinue = false;
        }
    }
}