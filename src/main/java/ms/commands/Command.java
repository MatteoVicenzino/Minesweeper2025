package ms.commands;

import ms.model.Position;

/**
 * The {@code Command} class represents a user command.
 * It can be a positional command (reveal, flag) or non-positional command (help, quit, reset).
 */
public class Command {
    private final CommandType type;
    private final Position position;

    /**
     * Constructs a command with a specific type and position.
     *
     * @param type the type of command
     * @param position the position associated with the command
     */
    public Command(CommandType type, Position position) {
        this.type = type;
        this.position = position;
    }

    /**
     * Constructs a command without a position, used for non-positional commands.
     *
     * @param type the type of command
     */
    public Command(CommandType type) {
        this.type = type;
        this.position = null;
    }

    /**
     * Gets the type of this command.
     *
     * @return the command type
     */
    public CommandType getType() {
        return type;
    }

    /**
     * Gets the position associated with this command.
     *
     * @return the position, or null if this is a non-positional command
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Checks if this command has an associated position.
     *
     * @return true if the command has a position, false otherwise
     */
    public boolean hasPosition() {
        return position != null;
    }

    /**
     * Gets the row of the position associated with this command.
     *
     * @return the row, or -1 if there is no position
     */
    public int getRow() {
        return position != null ? position.row() : -1;
    }

    /**
     * Gets the column of the position associated with this command.
     *
     * @return the column, or -1 if there is no position
     */
    public int getCol() {
        return position != null ? position.col() : -1;
    }

    /**
     * Returns a string representation of this command.
     */
    @Override
    public String toString() {
        if (hasPosition()) {
            return "Command{" +
                    "type=" + type +
                    ", position=" + position +
                    '}';
        } else {
            return "Command{" +
                    "type=" + type +
                    '}';
        }
    }
}