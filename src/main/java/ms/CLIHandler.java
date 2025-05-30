package ms;

import java.util.Scanner;

public class CLIHandler {
    private final CommandParser parser;
    private final Game game;
    private Scanner scanner;

    public CLIHandler(CommandParser parser, Game game) {
        this.parser = parser;
        this.game = game;
    }

    public Command handleInput(String input) {
        return parser.parse(input);
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

    private void displayHelp() {
        System.out.println("""
                
                === MINESWEEPER HELP ===
                Available commands:
                  reveal <row>,<col>  - Reveal a cell at the specified coordinates
                  flag <row>,<col>    - Toggle flag on a cell at the specified coordinates
                  help                - Show this help message
                  quit                - Exit the game
                
                Examples:
                  reveal 3,4          - Reveals the cell at row 3, column 4
                  flag 0,0            - Toggles flag on cell at row 0, column 0
                
                Game Rules:
                  • The goal is to reveal all cells that don't contain mines
                  • Numbers show how many mines are adjacent to that cell
                  • Use flags to mark cells you think contain mines
                  • If you reveal a mine, you lose!
                  • Your first reveal is always safe
                
                Field Symbols:
                  -  = Covered cell
                  F  = Flagged cell
                  1-8= Number of adjacent mines
                  (space) = Empty cell (no adjacent mines)
                  X  = Exploded mine
                  *  = Mine (shown only when game ends)
                ========================""");
    }

    public void start() {
        System.out.println("""
            Welcome to the Minesweeper CLI! Enter your commands:
            Type 'help' for available commands and game rules.""");

        displayGameStatus();

        Scanner currentScanner = getScanner();

        while (currentScanner.hasNextLine()) {
            System.out.print("> ");
            String inputLine = currentScanner.nextLine();

            try {
                Command command = handleInput(inputLine);

                switch (command.getType()) {
                    case HELP:
                        displayHelp();
                        continue;
                    case QUIT:
                        System.out.println("Exiting the game. Goodbye!");
                        return;
                    default:
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayGameStatus() {
        System.out.println("\n--- Current Minefield ---");
        System.out.println("Game status display - basic implementation");
        System.out.println("-------------------------");
    }
}