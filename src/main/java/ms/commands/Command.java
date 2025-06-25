package ms.commands;

import ms.model.Position;

/**
 * The {@code Command} class represents a user command.
 * It can be a positional command (reveal, flag) or non-positional command (help, quit, reset).
 */
public class Command {
    private final CommandType type;
    private final Position position;

    public Command(CommandType type, Position position) {
        this.type = type;
        this.position = position;
    }

    public Command(CommandType type) {
        this.type = type;
        this.position = null;
    }

    public CommandType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public boolean hasPosition() {
        return position != null;
    }

    public int getRow() {
        return position != null ? position.row() : -1;
    }

    public int getCol() {
        return position != null ? position.col() : -1;
    }

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