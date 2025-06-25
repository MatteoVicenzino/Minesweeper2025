package ms.view;

import ms.commands.Command;
import ms.commands.CommandParser;
import ms.logic.Game;
import ms.model.Difficulty;

/**
 * Handles the command-line interface for the Minesweeper game.
 * Manages user input, game state, and display output.
 */
public class CLIHandler {
    private final InputManager inputManager;
    private final DisplayManager displayManager;
    private Game game;
    private boolean shouldContinue = true;

    /**
     * Constructs a CLIHandler with a specified CommandParser and Game instance.
     *
     * @param parser The CommandParser used to parse user input.
     * @param game   The Game instance to be managed.
     */
    public CLIHandler(CommandParser parser, Game game) {
        this.inputManager = new InputManager(parser);
        this.displayManager = new DisplayManager();
        this.game = game;
    }

    /**
     * Constructs a CLIHandler with a specified CommandParser.
     * The Game instance will be initialized later based on user input.
     *
     * @param parser The CommandParser used to parse user input.
     */
    public CLIHandler(CommandParser parser) {
        this.inputManager = new InputManager(parser);
        this.displayManager = new DisplayManager();
        this.game = null;
    }

    /**
     * Processes the user input and returns the corresponding Command.
     *
     * @param input The user input string to be processed.
     * @return The Command object representing the parsed input.
     */
    public Command handleInput(String input) {
        return inputManager.handleInput(input);
    }

    /**
     * Sets the Scanner instance to be used for input.
     *
     * @param scanner The Scanner instance to be used.
     */
    public void setScanner(java.util.Scanner scanner) {
        inputManager.setScanner(scanner);
    }

    /**
     * Starts the CLI game loop, handling user input and game state until the game ends.
     */
    public void run() {
        setupInitialGame();
        runMainLoop();
        inputManager.cleanup();
    }

    /**
     * Initializes the game setup, including difficulty selection and initial display.
     */
    private void setupInitialGame() {
        displayManager.displayWelcome();

        if (game == null) {
            Difficulty selectedDifficulty = inputManager.selectDifficulty();
            this.game = new Game(selectedDifficulty);
        }

        displayManager.displayGameStatus(game);
    }

    /**
     * Runs the main game loop, processing user commands and updating the game state.
     */
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

    /**
     * Handles the game over state, displaying results and prompting for replay.
     */
    private void handleGameOver() {
        displayManager.displayGameOver(game);
        handlePlayAgainChoice();
    }

    /**
     * Prompts the user to play again or change difficulty after a game ends.
     */
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