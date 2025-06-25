package ms.view;

import ms.commands.Command;
import ms.commands.CommandParser;
import ms.model.Difficulty;

import java.util.Scanner;

/**
 * Manages user input for the Minesweeper game.
 * Handles reading and processing input from the user.
 */
public class InputManager {
    private final CommandParser parser;
    private Scanner scanner;

    /**
     * Constructs an InputManager with a specified CommandParser.
     *
     * @param parser The CommandParser used to parse user input.
     */
    public InputManager(CommandParser parser) {
        this.parser = parser;
    }

    /**
     * Gets the Scanner instance, initializing it if necessary.
     *
     * @return The Scanner instance for reading input.
     */
    private Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    /**
     * Sets the Scanner instance to be used for input.
     *
     * @param scanner The Scanner instance to be used.
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Processes the user input and returns the corresponding Command.
     *
     * @param input The user input string to be processed.
     * @return The Command object representing the parsed input.
     */
    public Command handleInput(String input) {
        return parser.parse(input);
    }

    /**
     * Prompts the user to select a difficulty level.
     *
     * @return The selected Difficulty level.
     */
    public Difficulty selectDifficulty() {
        System.out.println(Messages.DIFFICULTY_SELECTION_HEADER);

        Scanner currentScanner = getScanner();

        while (true) {
            System.out.print(Messages.COMMAND_PROMPT);
            String input = currentScanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.println(Messages.SELECTED_EASY);
                    return Difficulty.EASY;
                case "2":
                    System.out.println(Messages.SELECTED_MEDIUM);
                    return Difficulty.MEDIUM;
                case "3":
                    System.out.println(Messages.SELECTED_HARD);
                    return Difficulty.HARD;
                default:
                    System.out.println(Messages.DIFFICULTY_INVALID_CHOICE);
                    break;
            }
        }
    }

    /**
     * Closes the Scanner and cleans up resources.
     */
    public void cleanup() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }

    /**
     * Prompts the user to choose whether to play again.
     *
     * @return The user's choice ("yes", "no", or "change").
     */
    public String readPlayAgainChoice() {
        Scanner currentScanner = getScanner();
        System.out.print(Messages.PLAY_AGAIN_PROMPT);
        return currentScanner.nextLine().trim().toLowerCase();
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The user's input line.
     */
    public String readInputLine() {
        Scanner currentScanner = getScanner();
        System.out.print(Messages.COMMAND_PROMPT);
        return currentScanner.nextLine();
    }

    /**
     * Checks if there is more input available.
     *
     * @return True if there is more input, false otherwise.
     */
    public boolean hasNextLine() {
        return getScanner().hasNextLine();
    }
}