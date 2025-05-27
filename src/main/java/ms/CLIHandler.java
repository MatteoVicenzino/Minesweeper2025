package ms;

import java.util.Scanner;

public class CLIHandler {
    private final CommandParser parser;
    private final Game game;
    private Scanner scanner;

    public CLIHandler(CommandParser parser, Game game) {
        this.parser = parser;
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    public Command handleInput(String input) {
        return parser.parse(input);
    }
}