package ms.commands;

import ms.model.Position;

import java.util.Locale;

/**
 * The {@code CommandParser} class parses user input strings into {@code Command} objects.
 * It validates command syntax and coordinates, throwing {@code CommandParsingException} for invalid input.
 */
public class CommandParser {

    /**
     * Parses the input string and returns a {@code Command} object.
     *
     * @param input the user input string
     * @return a {@code Command} object representing the parsed command
     * @throws CommandParsingException if the input is invalid or cannot be parsed
     */
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


    /**
     * Parses commands for revealing or flagging a cell.
     *
     * @param parts the split input parts containing command and coordinates
     * @param type the type of command (REVEAL or FLAG)
     * @return a {@code Command} object with the specified type and position
     * @throws CommandParsingException if the coordinates are invalid
     */
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

    /**
     * Extracts and validates coordinates from the command.
     *
     * @param parts the list of string containing command and coordinates
     * @return an array of strings representing the coordinates
     * @throws CommandParsingException if the coordinates are missing or invalid
     */
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


    /**
     * The {@code CommandParsingException} is the class of the exception thrown when command parsing fails.
     */
    public static class CommandParsingException extends RuntimeException {
        public CommandParsingException(String message) {
            super(message);
        }
    }
}