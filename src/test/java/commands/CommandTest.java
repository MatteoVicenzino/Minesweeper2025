package commands;

import ms.commands.Command;
import ms.commands.CommandType;
import ms.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {

    @Test
    void testCreateCommandWithValidCoordinatesStoresPositionCorrectly() {
        Position position = new Position(3, 4);
        Command command = new Command(CommandType.REVEAL, position);

        assertEquals(CommandType.REVEAL, command.getType(), "Type should match");
        assertTrue(command.hasPosition(), "Command should have position");
        assertEquals(position, command.getPosition(), "Position should match");

        assertEquals(3, command.getRow(), "Row should match");
        assertEquals(4, command.getCol(), "Column should match");
    }

    @Test
    void testCreateCommandWithoutCoordinatesHasNoPosition() {
        Command command = new Command(CommandType.QUIT);

        assertEquals(CommandType.QUIT, command.getType(), "Type should match");
        assertFalse(command.hasPosition(), "Command should not have position");
        assertEquals(-1, command.getRow(), "Row should be -1 for commands without coordinates");
        assertEquals(-1, command.getCol(), "Column should be -1 for commands without coordinates");
    }

    @Test
    void testHelpCommand() {
        Command command = new Command(CommandType.HELP);

        assertEquals(CommandType.HELP, command.getType(), "Type should be HELP");
        assertFalse(command.hasPosition(), "Command should not have position");
        assertEquals(-1, command.getRow(), "Row should be -1 for HELP command");
        assertEquals(-1, command.getCol(), "Column should be -1 for HELP command");
    }

    @Test
    void testCommandWithZeroCoordinates() {
        Position position = new Position(0, 0);
        Command command = new Command(CommandType.FLAG, position);

        assertEquals(CommandType.FLAG, command.getType(), "Type should match");
        assertTrue(command.hasPosition(), "Command should have position");
        assertEquals(0, command.getRow(), "Row should be 0");
        assertEquals(0, command.getCol(), "Column should be 0");
    }

    @Test
    void testCommandWithNegativeCoordinates() {
        Position position = new Position(-5, -3);
        Command command = new Command(CommandType.REVEAL, position);

        assertEquals(CommandType.REVEAL, command.getType(), "Type should match");
        assertTrue(command.hasPosition(), "Command should have position");
        assertEquals(-5, command.getRow(), "Row should be -5");
        assertEquals(-3, command.getCol(), "Column should be -3");
    }

    @Test
    void testCommandWithLargeCoordinates() {
        Position position = new Position(999, 888);
        Command command = new Command(CommandType.FLAG, position);

        assertEquals(CommandType.FLAG, command.getType(), "Type should match");
        assertTrue(command.hasPosition(), "Command should have position");
        assertEquals(999, command.getRow(), "Row should be 999");
        assertEquals(888, command.getCol(), "Column should be 888");
    }
}