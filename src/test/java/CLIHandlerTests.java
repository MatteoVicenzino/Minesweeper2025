import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ms.CommandParser;
import ms.CLIHandler;
import ms.Command;
import ms.CommandType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ms.Game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class CLIHandlerTests {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @Test
    void testHandleValidInput() {
        CommandParser parser = new CommandParser();
        Game game = new Game(10, 10, 10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        String input = "reveal 3,4";
        Command command = cliHandler.handleInput(input);

        assertNotNull(command, "Command should not be null for valid input");
        assertEquals(CommandType.REVEAL, command.getType(), "Command type should be REVEAL");
        assertEquals(3, command.getRow(), "Row should match the input");
        assertEquals(4, command.getCol(), "Column should match the input");
    }

    @Test
    void testHandleInvalidInput() {
        CommandParser parser = new CommandParser();
        Game game = new Game(10, 10, 10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        String input = "invalid command";
        assertThrows(IllegalArgumentException.class, () -> cliHandler.handleInput(input),
                "Invalid input should throw an exception");
    }

    @Test
    void testHandleFlagCommand() {
        CommandParser parser = new CommandParser();
        Game game = new Game(10, 10, 10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        String input = "flag 5,6";
        Command command = cliHandler.handleInput(input);

        assertNotNull(command);
        assertEquals(CommandType.FLAG, command.getType());
        assertEquals(5, command.getRow());
        assertEquals(6, command.getCol());
    }

    @Test
    void testHandleQuitCommand() {
        CommandParser parser = new CommandParser();
        Game game = new Game(10, 10, 10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        String input = "quit";
        Command command = cliHandler.handleInput(input);

        assertNotNull(command);
        assertEquals(CommandType.QUIT, command.getType());
        assertEquals(-1, command.getRow(), "Row should be -1 for QUIT command");
        assertEquals(-1, command.getCol(), "Column should be -1 for QUIT command");
    }

    @Test
    void testHandleMalformedCoordinate() {
        CommandParser parser = new CommandParser();
        Game game = new Game(10, 10, 10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        String input = "reveal 3-4";
        assertThrows(IllegalArgumentException.class, () -> cliHandler.handleInput(input),
                "Malformed coordinate should cause an exception");
    }

    @Test
    void testHandleHelpCommand() {
        CommandParser parser = new CommandParser();
        Game game = new Game(10, 10, 10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        String input = "help";
        Command command = cliHandler.handleInput(input);

        assertNotNull(command);
        assertEquals(CommandType.HELP, command.getType());
        assertEquals(-1, command.getRow(), "Row should be -1 for HELP command");
        assertEquals(-1, command.getCol(), "Column should be -1 for HELP command");
    }

    @Test
    void testHelpDisplayContent() {
        CommandParser parser = new CommandParser();
        Game game = new Game(10, 10, 10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        String input = "help\nquit\n";
        provideInput(input);
        cliHandler.setScanner(new Scanner(System.in));

        cliHandler.start();
        String output = outContent.toString();

        assertTrue(output.contains("=== MINESWEEPER HELP ==="),
                "Should show help header");
        assertTrue(output.contains("Available commands:"),
                "Should show available commands");
        assertTrue(output.contains("reveal <row>,<col>"),
                "Should explain reveal command");
        assertTrue(output.contains("Field Symbols:"),
                "Should show field symbols");
        assertTrue(output.contains("F  = Flagged cell"),
                "Should explain flag symbol");
        assertTrue(output.contains("========================"),
                "Should show help footer");
    }
}