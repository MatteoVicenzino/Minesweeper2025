package ms.view;

import ms.commands.Command;
import ms.commands.CommandParser;
import ms.logic.Game;
import ms.model.Difficulty;

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

    public void run() {
        setupInitialGame();
        runMainLoop();
        inputManager.cleanup();
    }

    private void setupInitialGame() {
        displayManager.displayWelcome();

        if (game == null) {
            Difficulty selectedDifficulty = inputManager.selectDifficulty();
            this.game = new Game(selectedDifficulty);
        }

        displayManager.displayGameStatus(game);
    }

    private void runMainLoop() {
        while (inputManager.hasNextLine() && shouldContinue) {
            String inputLine = inputManager.readInputLine();

            try {
                Command command = handleInput(inputLine);

                switch (command.getType()) {
                    case REVEAL:
                        if (command.hasPosition()) {
                            game.revealCell(command.getPosition());
                        }
                        break;
                    case FLAG:
                        if (command.hasPosition()) {
                            game.flagCell(command.getPosition());
                        }
                        break;
                    case HELP:
                        displayManager.displayHelp();
                        continue;
                    case RESET:
                        game.resetGame();
                        displayManager.displayGameReset();
                        break;
                    case QUIT:
                        displayManager.displayGoodbye();
                        return;
                }

                displayManager.displayGameStatus(game);

                if (game.isGameOver()) {
                    handleGameOver();
                }

            } catch (CommandParser.CommandParsingException e) {
                displayManager.displayParsingError(e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                displayManager.displayCoordinateError(e.getMessage());
            } catch (Game.InvalidGameOperationException e) {
                displayManager.displayGameError(e.getMessage());
            }
        }
    }

    private void handleGameOver() {
        displayManager.displayGameOver(game);
        handlePlayAgainChoice();
    }

    private void handlePlayAgainChoice() {
        String playAgainInput = inputManager.readPlayAgainChoice();

        switch (playAgainInput) {
            case "yes":
                game.resetGame();
                displayManager.displayNewGame();
                displayManager.displayGameStatus(game);
                break;
            case "change":
                Difficulty newDifficulty = inputManager.selectDifficulty();
                this.game = new Game(newDifficulty);
                displayManager.displayNewGameWithDifficulty();
                displayManager.displayGameStatus(game);
                break;
            case "no":
            default:
                displayManager.displayThankYou();
                displayManager.displayGoodbye();
                shouldContinue = false;
                inputManager.cleanup();
                break;
        }
    }
}