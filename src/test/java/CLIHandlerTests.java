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

import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import ms.GameStatus;
import ms.Position;
import ms.MineField;
import ms.Cell;
import static org.mockito.ArgumentMatchers.any;

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

    @Test
    void testWelcomeMessage() {
        CommandParser parser = new CommandParser();
        Game game = new Game(10,10,10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        String input = "quit\n";
        provideInput(input);
        cliHandler.setScanner(new Scanner(System.in));

        cliHandler.start();
        String output = outContent.toString();

        assertTrue(output.contains("Welcome to the Minesweeper CLI!"),
                "Should show welcome message");
        assertTrue(output.contains("Type 'help' for available commands"),
                "Should show initial help hint");
        assertTrue(output.contains("Exiting the game. Goodbye!"),
                "Should show exit message");
    }

    @Test
    void testDisplayUninitializedField() {
        CommandParser parser = new CommandParser();
        Game game = new Game(10,10,10);
        CLIHandler cliHandler = new CLIHandler(parser, game);

        String input = "quit\n";
        provideInput(input);
        cliHandler.setScanner(new Scanner(System.in));

        cliHandler.start();
        String output = outContent.toString();

        assertTrue(output.contains("Minefield not yet initialized"),
                "Should show uninitialized message");
        assertTrue(output.contains("Grid Size: 10x10"),
                "Should show grid size");
        assertTrue(output.contains("Mines: 10"),
                "Should show mine count");
        assertTrue(output.contains("Type 'help'"),
                "Should show help hint");
    }


    @Test
    void testGameLoopInteractions() {
        CommandParser parser = new CommandParser();
        Game mockGame = Mockito.mock(Game.class);
        MineField mockMineField = Mockito.mock(MineField.class);
        CLIHandler cliHandler = new CLIHandler(parser, mockGame);

        when(mockGame.getRevealed()).thenReturn(1);
        when(mockGame.getGameOver()).thenReturn(false, true);
        when(mockGame.getGameStatus()).thenReturn(GameStatus.WON);
        when(mockGame.getElapsedTime()).thenReturn(30000L);
        when(mockGame.getMinefield()).thenReturn(mockMineField);
        when(mockGame.getTotalMines()).thenReturn(10);

        when(mockMineField.getWidth()).thenReturn(10);
        when(mockMineField.getHeight()).thenReturn(10);

        Cell mockCell = Mockito.mock(Cell.class);
        when(mockCell.isRevealed()).thenReturn(false);
        when(mockCell.isFlagged()).thenReturn(false);
        when(mockCell.isMined()).thenReturn(false);
        when(mockMineField.getCell(any(Position.class))).thenReturn(mockCell);
        when(mockMineField.countAdjacentMines(any(Position.class))).thenReturn(0);

        String input = "reveal 3,4\nno\n";
        provideInput(input);
        cliHandler.setScanner(new Scanner(System.in));

        cliHandler.start();

        verify(mockGame).revealCell(new Position(3, 4));
        verify(mockGame, atLeastOnce()).getGameOver();
        verify(mockGame).getGameStatus();
    }

    @Test
    void testDisplayFormatting() {
        CommandParser parser = new CommandParser();
        Game mockGame = Mockito.mock(Game.class);
        MineField mockMineField = Mockito.mock(MineField.class);
        CLIHandler cliHandler = new CLIHandler(parser, mockGame);

        when(mockGame.getRevealed()).thenReturn(1);
        when(mockGame.getGameOver()).thenReturn(false);
        when(mockGame.getMinefield()).thenReturn(mockMineField);
        when(mockGame.getTotalMines()).thenReturn(10);
        when(mockGame.getFlagsPlaced()).thenReturn(2);
        when(mockGame.getElapsedTime()).thenReturn(45000L);

        when(mockMineField.getWidth()).thenReturn(10);
        when(mockMineField.getHeight()).thenReturn(10);

        Cell mockCell = Mockito.mock(Cell.class);
        when(mockCell.isRevealed()).thenReturn(false);
        when(mockCell.isFlagged()).thenReturn(false);
        when(mockCell.isMined()).thenReturn(false);
        when(mockMineField.getCell(any(Position.class))).thenReturn(mockCell);
        when(mockMineField.countAdjacentMines(any(Position.class))).thenReturn(0);

        String input = "quit\n";
        provideInput(input);
        cliHandler.setScanner(new Scanner(System.in));

        cliHandler.start();
        String output = outContent.toString();

        assertTrue(output.contains("--- Current Minefield ---"),
                "Should show minefield header");

        assertTrue(output.contains(" 0  1  2  3  4  5  6  7  8  9"),
                "Should show column numbers 0-9");

        assertTrue(output.contains("  +"),
                "Should show horizontal separator");

        assertTrue(output.contains(" 0|") && output.contains(" 5|") && output.contains(" 9|"),
                "Should show row numbers for all rows");

        assertTrue(output.contains(" - "),
                "Should show covered cells with '-' symbol");

        assertTrue(output.contains("Revealed: 1 / 90"),
                "Should show correct revealed count (1 out of 90 non-mine cells)");
        assertTrue(output.contains("Flags: 2 / 10"),
                "Should show correct flag count (2 out of 10 total mines)");
        assertTrue(output.contains("Time: 45s"),
                "Should show correct elapsed time (45 seconds)");

        assertTrue(output.contains("-------------------------"),
                "Should show footer separator");
    }
}