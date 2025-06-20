package ms.commands;

import ms.Position;
import java.util.Locale;

public class CommandParser {

    public Command parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        input = input.trim().replaceAll("\\s+", " ");
        String[] parts = input.split(" ", 2);

        String commandType = parts[0].toUpperCase(Locale.ROOT);

        return switch (commandType) {
            case "REVEAL" -> parseRevealOrFlagCommand(parts, CommandType.REVEAL);
            case "FLAG" -> parseRevealOrFlagCommand(parts, CommandType.FLAG);
            case "HELP" -> {
                if (parts.length > 1) {
                    throw new IllegalArgumentException("Help command does not take any arguments");
                }
                yield new Command(CommandType.HELP);
            }
            case "QUIT" -> {
                if (parts.length > 1) {
                    throw new IllegalArgumentException("Quit command does not take any arguments");
                }
                yield new Command(CommandType.QUIT);
            }
            case "RESET" -> {
                if (parts.length > 1) {
                    throw new IllegalArgumentException("Reset command does not take any arguments");
                }
                yield new Command(CommandType.RESET);
            }
            default -> throw new IllegalArgumentException("Unknown command type: " + commandType);
        };
    }

    private Command parseRevealOrFlagCommand(String[] parts, CommandType type) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Missing coordinates");
        }

        String coordinatesPart = parts[1].trim();

        if (!coordinatesPart.contains(",")) {
            throw new IllegalArgumentException("Invalid coordinate format");
        }

        String[] coordinates = coordinatesPart.split("\\s*,\\s*");

        if (coordinates.length != 2 || coordinates[0].isEmpty() || coordinates[1].isEmpty()) {
            throw new IllegalArgumentException("Invalid coordinate value");
        }

        try {
            int row = Integer.parseInt(coordinates[0].trim());
            int col = Integer.parseInt(coordinates[1].trim());
            Position position = new Position(row, col);
            return new Command(type, position);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordinate value");
        }
    }
}