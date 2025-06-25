package ms;

import ms.commands.CommandParser;
import ms.view.CLIHandler;

/**
 * The Main class serves as the entry point for the Minesweeper game application.
 * It initializes the CommandParser and CLIHandler, and starts the game loop.
 */
public class Main {

    /**
     * The main method initializes the CommandParser and CLIHandler,
     * and starts the game loop.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        CLIHandler cliHandler = new CLIHandler(parser);
        cliHandler.run();
    }
}