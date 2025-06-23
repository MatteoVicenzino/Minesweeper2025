package ms.commands;

import ms.model.Position;

import java.util.Locale;

public class CommandParser {

    public Command parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new CommandParsingException("Input cannot be empty");
        }

        input = input.trim().replaceAll("\\s+", " ");
        String[] parts = input.split(" ", 2);

        String commandType = parts[0].toUpperCase(Locale.ROOT);

        return switch (commandType) {
            case "REVEAL" -> parseRevealOrFlagCommand(parts, CommandType.REVEAL);
            case "FLAG" -> parseRevealOrFlagCommand(parts, CommandType.FLAG);
            case "HELP" -> {
                if (parts.length > 1) {
                    throw new CommandParsingException("Help command does not take any arguments");
                }
                yield new Command(CommandType.HELP);
            }
            case "QUIT" -> {
                if (parts.length > 1) {
                    throw new CommandParsingException("Quit command does not take any arguments");
                }
                yield new Command(CommandType.QUIT);
            }
            case "RESET" -> {
                if (parts.length > 1) {
                    throw new CommandParsingException("Reset command does not take any arguments");
                }
                yield new Command(CommandType.RESET);
            }
            default -> throw new CommandParsingException("Unknown command type: " + commandType);
        };
    }

    private Command parseRevealOrFlagCommand(String[] parts, CommandType type) {
        String[] coordinates = getCoordinates(parts);

        try {
            int row = Integer.parseInt(coordinates[0].trim());
            int col = Integer.parseInt(coordinates[1].trim());
            Position position = new Position(row, col);
            return new Command(type, position);
        } catch (NumberFormatException e) {
            throw new CommandParsingException("Invalid coordinate value");
        }
    }

    private static String[] getCoordinates(String[] parts) {
        if (parts.length < 2) {
            throw new CommandParsingException("Missing coordinates");
        }

        String coordinatesPart = parts[1].trim();

        if (!coordinatesPart.contains(",")) {
            throw new CommandParsingException("Invalid coordinate format");
        }

        String[] coordinates = coordinatesPart.split("\\s*,\\s*");

        if (coordinates.length != 2 || coordinates[0].isEmpty() || coordinates[1].isEmpty()) {
            throw new CommandParsingException("Invalid coordinate value");
        }
        return coordinates;
    }

    public static class CommandParsingException extends RuntimeException {
        public CommandParsingException(String message) {
            super(message);
        }
    }
}