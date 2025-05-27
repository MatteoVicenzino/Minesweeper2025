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

    @Test
    void testParseMalformedCoordinateMissingComma() {
        CommandParser parser = new CommandParser();
        String input = "reveal 3 4";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Malformed coordinate should throw an exception");
        assertTrue(thrown.getMessage().contains("Invalid coordinate format"), "Error message should indicate invalid coordinate format");
    }

    @Test
    void testParseMalformedCoordinateNonNumeric() {
        CommandParser parser = new CommandParser();
        String input = "reveal a,b";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Non-numeric coordinate should throw an exception");
        assertTrue(thrown.getMessage().contains("Invalid coordinate value"), "Error message should indicate non-numeric coordinate");
    }

    @Test
    void testParseMalformedCoordinatePartialNumeric() {
        CommandParser parser = new CommandParser();
        String input = "reveal 3,b";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Partial non-numeric coordinate should throw an exception");
        assertTrue(thrown.getMessage().contains("Invalid coordinate value"), "Error message should indicate non-numeric coordinate");
    }

    @Test
    void testParseMissingCoordinateValue() {
        CommandParser parser = new CommandParser();
        String input = "reveal 3,";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Missing coordinate value should throw an exception");
        assertTrue(thrown.getMessage().contains("Invalid coordinate value"), "Error message for missing coordinate value");
    }

    @Test
    void testParseMissingCoordinateValueFirst() {
        CommandParser parser = new CommandParser();
        String input = "reveal ,4";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Missing coordinate value should throw an exception");
        assertTrue(thrown.getMessage().contains("Invalid coordinate value"), "Error message for missing coordinate value");
    }

    @Test
    void testParseWithExtraSpacesAroundCommandAndCoords() {
        CommandParser parser = new CommandParser();
        String input = "  reveal   3 , 4  ";

        Command command = parser.parse(input);

        assertNotNull(command);
        assertEquals(CommandType.REVEAL, command.getType());
        assertEquals(3, command.getRow(), "Row should match the input");
        assertEquals(4, command.getCol(), "Column should match the input");
    }

    @Test
    void testParseCaseInsensitiveCommandType() {
        CommandParser parser = new CommandParser();
        String input = "ReVeAl 1,1";

        Command command = parser.parse(input);

        assertNotNull(command);
        assertEquals(CommandType.REVEAL, command.getType());
        assertEquals(1, command.getRow(), "Row should match the input");
        assertEquals(1, command.getCol(), "Column should match the input");
    }

    @Test
    void testParseNegativeCoordinates() {
        CommandParser parser = new CommandParser();
        String input = "reveal -1,-2";

        Command command = parser.parse(input);

        assertNotNull(command);
        assertEquals(CommandType.REVEAL, command.getType());
        assertEquals(-1, command.getRow(), "Row should match the input");
        assertEquals(-2, command.getCol(), "Column should match the input");
    }

    @Test
    void testParseValidHelpCommand() {
        CommandParser parser = new CommandParser();
        String input = "help";

        Command command = parser.parse(input);

        assertNotNull(command, "Command should not be null for HELP");
        assertEquals(CommandType.HELP, command.getType(), "Command type should be HELP");
        assertEquals(-1, command.getRow(), "Row should be -1 for HELP command");
        assertEquals(-1, command.getCol(), "Column should be -1 for HELP command");
    }

    @Test
    void testParseHelpCommandWithExtraArguments() {
        CommandParser parser = new CommandParser();
        String input = "help 1,2";

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> parser.parse(input),
                "Help command with extra arguments should throw an exception");
        assertTrue(thrown.getMessage().contains("Help command does not take any arguments"), "Error message for help with args");
    }

    @Test
    void testParseCaseInsensitiveHelpCommand() {
        CommandParser parser = new CommandParser();
        String input = "HELP";

        Command command = parser.parse(input);

        assertNotNull(command);
        assertEquals(CommandType.HELP, command.getType());
    }
}