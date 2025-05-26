import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ms.Command;
import ms.CommandType;

public class CommandTests {

    @Test
    void testCommandWithCoordinates() {
        Command command = new Command(CommandType.REVEAL, 3, 4);

        assertEquals(CommandType.REVEAL, command.getType(), "Type should match");
        assertEquals(3, command.getRow(), "Row should match");
        assertEquals(4, command.getCol(), "Column should match");
    }

    @Test
    void testCommandWithoutCoordinates() {
        Command command = new Command(CommandType.QUIT);

        assertEquals(CommandType.QUIT, command.getType(), "Type should match");
        assertEquals(-1, command.getRow(), "Row should be -1 for commands without coordinates");
        assertEquals(-1, command.getCol(), "Column should be -1 for commands without coordinates");
    }

	@Test
    void testHelpCommand() {
    	Command command = new Command(CommandType.HELP);

    	assertEquals(CommandType.HELP, command.getType(), "Type should be HELP");
    	assertEquals(-1, command.getRow(), "Row should be -1 for HELP command");
    	assertEquals(-1, command.getCol(), "Column should be -1 for HELP command");
    }

    @Test
    void testCommandWithZeroCoordinates() {
        Command command = new Command(CommandType.FLAG, 0, 0);

        assertEquals(CommandType.FLAG, command.getType(), "Type should match");
        assertEquals(0, command.getRow(), "Row should be 0");
        assertEquals(0, command.getCol(), "Column should be 0");
    }

    @Test
    void testCommandWithNegativeCoordinates() {
        Command command = new Command(CommandType.REVEAL, -5, -3);

        assertEquals(CommandType.REVEAL, command.getType(), "Type should match");
        assertEquals(-5, command.getRow(), "Row should be -5");
        assertEquals(-3, command.getCol(), "Column should be -3");
    }

    @Test
    void testCommandWithLargeCoordinates() {
        Command command = new Command(CommandType.FLAG, 999, 888);

        assertEquals(CommandType.FLAG, command.getType(), "Type should match");
        assertEquals(999, command.getRow(), "Row should be 999");
        assertEquals(888, command.getCol(), "Column should be 888");
    }
}