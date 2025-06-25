package ms.commands;

/**
 * The {@code CommandType} enumeration defines the available command types
 * that can be executed
 */
public enum CommandType {
    /**
     * Command to reveal a cell in the game.
     */
    REVEAL,

    /**
     * Command to flag a cell in the game.
     */
    FLAG,

    /**
     * Command to display the current game status.
     */
    HELP,

    /**
     * Command to display the current game statistics.
     */
    QUIT,

    /**
     * Command to reset the game.
     */
    RESET
}