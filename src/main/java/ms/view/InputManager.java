package ms.view;

import ms.commands.Command;
import ms.commands.CommandParser;
import ms.model.Difficulty;

import java.util.Scanner;

public class InputManager {
    private final CommandParser parser;
    private Scanner scanner;

    public InputManager(CommandParser parser) {
        this.parser = parser;
    }

    private Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Command handleInput(String input) {
        return parser.parse(input);
    }

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

    public void cleanup() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }

    public String readPlayAgainChoice() {
        Scanner currentScanner = getScanner();
        System.out.print(Messages.PLAY_AGAIN_PROMPT);
        return currentScanner.nextLine().trim().toLowerCase();
    }

    public String readInputLine() {
        Scanner currentScanner = getScanner();
        System.out.print(Messages.COMMAND_PROMPT);
        return currentScanner.nextLine();
    }

    public boolean hasNextLine() {
        return getScanner().hasNextLine();
    }
}