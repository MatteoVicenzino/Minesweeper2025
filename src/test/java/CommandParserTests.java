import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ms.CommandParser;
import ms.Command;
import ms.CommandType;

public class CommandParserTests {

    @Test
    void testParseValidRevealCommand() {
        CommandParser parser = new CommandParser();
        String input = "reveal 3,4";

        Command command = parser.parse(input);

        assertNotNull(command, "Command should not be null for valid input");
        assertEquals(CommandType.REVEAL, command.getType(), "Command type should be REVEAL");
        assertEquals(3, command.getRow(), "Row should match the input");
        assertEquals(4, command.getCol(), "Column should match the input");
    }

    @Test
    void testParseInvalidCommandType() {
        CommandParser parser = new CommandParser();
        String input = "invalid command";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Invalid input should throw an exception");
        assertTrue(thrown.getMessage().contains("Unknown command type"), "Error message should indicate unknown command type");
    }

    @Test
    void testParseValidFlagCommand() {
        CommandParser parser = new CommandParser();
        String input = "flag 5,6";

        Command command = parser.parse(input);

        assertNotNull(command, "Command should not be null for valid input");
        assertEquals(CommandType.FLAG, command.getType(), "Command type should be FLAG");
        assertEquals(5, command.getRow(), "Row should match the input");
        assertEquals(6, command.getCol(), "Column should match the input");
    }

    @Test
    void testParseValidQuitCommand() {
        CommandParser parser = new CommandParser();
        String input = "quit";

        Command command = parser.parse(input);

        assertNotNull(command, "Command should not be null for QUIT");
        assertEquals(CommandType.QUIT, command.getType(), "Command type should be QUIT");
        assertEquals(-1, command.getRow(), "Row should be -1 for QUIT command");
        assertEquals(-1, command.getCol(), "Column should be -1 for QUIT command");
    }

    @Test
    void testParseEmptyInput() {
        CommandParser parser = new CommandParser();
        String input = "";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Empty input should throw an exception");
        assertTrue(thrown.getMessage().contains("Input cannot be empty"), "Error message should indicate empty input");
    }

    @Test
    void testParseNullInput() {
        CommandParser parser = new CommandParser();
        String input = null;

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Null input should throw an exception");
        assertTrue(thrown.getMessage().contains("Input cannot be empty"), "Error message for null input");
    }

    @Test
    void testParseCommandWithoutCoordinates() {
        CommandParser parser = new CommandParser();
        String input = "reveal";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Command without coordinates should throw an exception");
        assertTrue(thrown.getMessage().contains("Missing coordinates"), "Error message should indicate missing coordinates");
    }

    @Test
    void testParseQuitCommandWithExtraArguments() {
        CommandParser parser = new CommandParser();
        String input = "quit 1,2";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Quit command with extra arguments should throw an exception");
        assertTrue(thrown.getMessage().contains("Quit command does not take any arguments"), "Error message for quit with args");
    }
}