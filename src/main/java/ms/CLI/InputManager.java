package ms.CLI;
import ms.*;

import java.util.Scanner;

public class InputManager {
    private final CommandParser parser;
    private Scanner scanner;

    public InputManager(CommandParser parser) {
        this.parser = parser;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    private Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    public Command handleInput(String input) {
        return parser.parse(input);
    }

    public Difficulty selectDifficulty() {
        System.out.println("""
                
                === DIFFICULTY SELECTION ===
                Choose your difficulty level:
                
                1. EASY   (9x9 grid, 10 mines)
                2. MEDIUM (16x16 grid, 40 mines)
                3. HARD   (16x30 grid, 99 mines)
                
                Enter your choice (1-3): """);

        Scanner currentScanner = getScanner();

        while (true) {
            System.out.print("> ");
            String input = currentScanner.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.println("Selected: EASY difficulty");
                    return Difficulty.EASY;
                case "2":
                    System.out.println("Selected: MEDIUM difficulty");
                    return Difficulty.MEDIUM;
                case "3":
                    System.out.println("Selected: HARD difficulty");
                    return Difficulty.HARD;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    break;
            }
        }
    }

    public String readPlayAgainChoice() {
        Scanner currentScanner = getScanner();
        System.out.print("Play again? (yes/no/change): ");
        return currentScanner.nextLine().trim().toLowerCase();
    }

    public String readInputLine() {
        Scanner currentScanner = getScanner();
        System.out.print("> ");
        return currentScanner.nextLine();
    }

    public boolean hasNextLine() {
        return getScanner().hasNextLine();
    }
}