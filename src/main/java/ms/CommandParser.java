package ms;

import java.util.Locale;

public class CommandParser {

    public Command parse(String input) {
        // Basic input trimming
        input = input.trim();
        String[] parts = input.split(" ", 2);

        String commandType = parts[0].toUpperCase(Locale.ROOT);

        return switch (commandType) {
            case "REVEAL" -> parseRevealOrFlagCommand(parts, CommandType.REVEAL);
            case "FLAG" -> parseRevealOrFlagCommand(parts, CommandType.FLAG);
            case "QUIT" -> new Command(CommandType.QUIT);
            default -> throw new IllegalArgumentException("Unknown command type: " + commandType);
        };
    }

    private Command parseRevealOrFlagCommand(String[] parts, CommandType type) {
        // Basic coordinate parsing - assume format "row,col"
        String coordinatesPart = parts[1];
        String[] coordinates = coordinatesPart.split(",");

        int row = Integer.parseInt(coordinates[0]);
        int col = Integer.parseInt(coordinates[1]);
        return new Command(type, row, col);
    }
}