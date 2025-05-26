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
}