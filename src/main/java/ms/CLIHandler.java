package ms;

import java.util.Scanner;

public class CLIHandler {
    private final CommandParser parser;
    private Game game;
    private Scanner scanner;

    public CLIHandler(CommandParser parser, Game game) {
        this.parser = parser;
        this.game = game;
    }

    public CLIHandler(CommandParser parser) {
        this.parser = parser;
        this.game = null;
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

    private Difficulty selectDifficulty() {
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

    private void displayGameStatus() {
        MineField mf = game.getMinefield();

        System.out.println("\n--- Current Minefield ---");

        if (game.getRevealed() == 0) {
            System.out.print("   ");
            for (int c = 0; c < mf.getWidth(); c++) {
                System.out.print(String.format("%2d ", c));
            }
            System.out.println();
            System.out.print("  +");
            for (int c = 0; c < mf.getWidth(); c++) {
                System.out.print("---");
            }
            System.out.println();

            for (int r = 0; r < mf.getHeight(); r++) {
                System.out.print(String.format("%2d|", r));
                for (int c = 0; c < mf.getWidth(); c++) {
                    System.out.print(" - ");
                }
                System.out.println();
            }

            System.out.println("-------------------------");
            System.out.printf("Revealed: %d / %d%n", game.getRevealed(), mf.getHeight() * mf.getWidth() - game.getTotalMines());
            System.out.printf("Flags: %d / %d%n", game.getFlagsPlaced(), game.getTotalMines());
            System.out.printf("Time: %ds%n", game.getElapsedTime() / 1000);
            System.out.println("-------------------------");

        }else{

            System.out.print("   ");
            for (int c = 0; c < mf.getWidth(); c++) {
                System.out.print(String.format("%2d ", c));
            }
            System.out.println();
            System.out.print("  +");
            for (int c = 0; c < mf.getWidth(); c++) {
                System.out.print("---");
            }
            System.out.println();

            for (int r = 0; r < mf.getHeight(); r++) {
                System.out.print(String.format("%2d|", r));
                for (int c = 0; c < mf.getWidth(); c++) {
                    Position pos = new Position(r, c);
                    Cell cell = mf.getCell(pos);

                    if (game.getGameOver() && cell.isMined() && !cell.isFlagged()) {
                        System.out.print(" * ");
                    } else if (cell.isRevealed()) {
                        if (cell.isMined()) {
                            System.out.print(" X ");
                        } else {
                            int adjacentMines = mf.countAdjacentMines(pos);
                            System.out.print(" " + (adjacentMines == 0 ? " " : adjacentMines) + " ");
                        }
                    } else if (cell.isFlagged()) {
                        System.out.print(" F ");
                    } else {
                        System.out.print(" - ");
                    }
                }
                System.out.println();
            }

            System.out.println("-------------------------");
            System.out.printf("Revealed: %d / %d%n", game.getRevealed(), mf.getHeight() * mf.getWidth() - game.getTotalMines());
            System.out.printf("Flags: %d / %d%n", game.getFlagsPlaced(), game.getTotalMines());
            System.out.printf("Time: %ds%n", game.getElapsedTime() / 1000);
            System.out.println("-------------------------");
        }
    }

    public void start() {
        System.out.println("""
                Welcome to the Minesweeper CLI! Enter your commands:
                Type 'help' for available commands and game rules.""");

        if (game == null) {
            Difficulty selectedDifficulty = selectDifficulty();
            this.game = new Game(selectedDifficulty);
        }

        displayGameStatus();

        Scanner currentScanner = getScanner();

        while (currentScanner.hasNextLine()) {
            System.out.print("> ");
            String inputLine = currentScanner.nextLine();

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
                        displayHelp();
                        continue;
                    case QUIT:
                        System.out.println("Exiting the game. Goodbye!");
                        return;
                }

                displayGameStatus();

                if (game.getGameOver()) {
                    System.out.println("\n--- GAME OVER ---");
                    if (game.getGameStatus() == GameStatus.WON) {
                        System.out.println("Congratulations! You revealed all non-mine cells! You Win!");
                    } else if (game.getGameStatus() == GameStatus.LOST) {
                        System.out.println("Boooom! You hit a mine! You Lose!");
                    }
                    System.out.printf("Time taken: %d seconds.%n", game.getElapsedTime() / 1000);

                    System.out.print("Play again? (yes/no/change): ");
                    String playAgainInput = currentScanner.nextLine().trim().toLowerCase();
                    if (playAgainInput.equals("yes")) {
                        game.resetGame();
                        System.out.println("\n--- Starting a new game ---");
                        displayGameStatus();
                    } else if (playAgainInput.equals("change")) {
                        Difficulty newDifficulty = selectDifficulty();
                        this.game = new Game(newDifficulty);
                        System.out.println("\n--- Starting a new game with new difficulty ---");
                        displayGameStatus();
                    } else {
                        System.out.println("Thank you for playing!");
                        return;
                    }

                }

            } catch (IllegalArgumentException e) {
                System.out.printf("""
                        Error: %s
                        Type 'help' for available commands.%n""", e.getMessage());
            } catch (IndexOutOfBoundsException e) {
                System.out.printf("""
                        Error: Coordinates are out of bounds. %s
                        Valid coordinates are 0-9 for both rows and columns.%n""", e.getMessage());
            }
        }
    }
}