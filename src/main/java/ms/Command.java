package ms;

public class Command {
    private final CommandType type;
    private final int row;
    private final int col;

    public Command(CommandType type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
    }

    public Command(CommandType type) {
        this(type, -1, -1);
    }

    public CommandType getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}