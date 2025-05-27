package ms;

import java.util.Locale;

public class CommandParser {

    public Command parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        input = input.trim();
        String[] parts = input.split(" ", 2);

        String commandType = parts[0].toUpperCase(Locale.ROOT);

        return switch (commandType) {
            case "REVEAL" -> parseRevealOrFlagCommand(parts, CommandType.REVEAL);
            case "FLAG" -> parseRevealOrFlagCommand(parts, CommandType.FLAG);
            case "QUIT" -> {
                if (parts.length > 1) {
                    throw new IllegalArgumentException("Quit command does not take any arguments");
                }
                yield new Command(CommandType.QUIT);
            }
            default -> throw new IllegalArgumentException("Unknown command type: " + commandType);
        };
    }

    private Command parseRevealOrFlagCommand(String[] parts, CommandType type) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing coordinates");
        }

        String coordinatesPart = parts[1];
        String[] coordinates = coordinatesPart.split(",");

        int row = Integer.parseInt(coordinates[0]);
        int col = Integer.parseInt(coordinates[1]);
        return new Command(type, row, col);
    }
}