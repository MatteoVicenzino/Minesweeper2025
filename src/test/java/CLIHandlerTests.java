import ms.commands.Command;
import ms.commands.CommandParser;
import ms.commands.CommandType;
import ms.logic.Game;
import ms.logic.status.GameStatus;
import ms.model.Cell;
import ms.model.Difficulty;
import ms.model.MineField;
import ms.model.Position;
import ms.view.CLIHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CLIHandlerTests {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private CommandParser parser;
    private Game mockGame;
    private CLIHandler cliHandler;

    @BeforeEach
    public void setUpStreamsAndMocks() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        parser = new CommandParser();
        mockGame = mock(Game.class);
        cliHandler = new CLIHandler(parser, mockGame);

        MineField mockMineField = mock(MineField.class);
        when(mockMineField.getWidth()).thenReturn(9);
        when(mockMineField.getHeight()).thenReturn(9);

        Cell mockCell = mock(Cell.class);
        when(mockCell.isRevealed()).thenReturn(false);
        when(mockCell.isFlagged()).thenReturn(false);
        when(mockCell.isMined()).thenReturn(false);
        when(mockMineField.getCell(any(Position.class))).thenReturn(mockCell);
        when(mockMineField.countAdjacentMines(any(Position.class))).thenReturn(0);

        when(mockGame.getGameOver()).thenReturn(false, true);
        when(mockGame.getGameStatus()).thenReturn(GameStatus.IN_PROGRESS);
        when(mockGame.getMinefield()).thenReturn(mockMineField);
        when(mockGame.getTotalMines()).thenReturn(10);
        when(mockGame.getRevealed()).thenReturn(0);
        when(mockGame.getFlagsPlaced()).thenReturn(0);
        when(mockGame.getElapsedTime()).thenReturn(0L);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    private String getOutput() {
        return outContent.toString();
    }

    private void assertOutputContains(String input, String... expectedContent) {
        provideInput(input);
        cliHandler.setScanner(new Scanner(System.in));
        cliHandler.run();

        String output = getOutput();
        for (String content : expectedContent) {
            assertTrue(output.contains(content), "Should contain: " + content);
        }
    }

    @Test
    void testHandleValidRevealCommand() {
        Command command = cliHandler.handleInput("reveal 3,4");

        assertAll("Valid Reveal Command",
                () -> assertNotNull(command, "Command should not be null"),
                () -> assertEquals(CommandType.REVEAL, command.getType(), "Command type should be REVEAL"),
                () -> assertTrue(command.hasPosition(), "Command should have position"),
                () -> assertEquals(new Position(3, 4), command.getPosition(), "Position should match input")
        );
    }

    @Test
    void testHandleValidFlagCommand() {
        provideInput("flag 5,6\nquit\n");
        cliHandler.setScanner(new Scanner(System.in));
        cliHandler.run();

        verify(mockGame).flagCell(new Position(5, 6));
    }

    @Test
    void testHandleQuitCommand() {
        assertOutputContains("quit\n", "Exiting the game. Goodbye!");
    }

    @Test
    void testHandleInvalidCommand() {
        cliHandler = new CLIHandler(parser, new Game(Difficulty.EASY));

        assertOutputContains("invalid command\nquit\n",
                "Error: Unknown command type",
                "Type 'help' for available commands"
        );
    }

    @Test
    void testHandleMalformedCoordinate() {
        cliHandler = new CLIHandler(parser, new Game(Difficulty.EASY));

        assertOutputContains("reveal 3-4\nquit\n",
                "Error:",
                "Type 'help' for available commands"
        );
    }

    @Test
    void testHandleHelpCommand() {
        assertOutputContains("help\nquit\n",
                "=== MINESWEEPER HELP ===",
                "Available commands:",
                "reveal <row>,<col>",
                "Field Symbols:",
                "F  = Flagged cell",
                "========================"
        );
    }

    @Test
    void testWelcomeMessage() {
        assertOutputContains("quit\n",
                "Welcome to the Minesweeper CLI!",
                "Type 'help' for available commands",
                "Exiting the game. Goodbye!"
        );
    }

    @Test
    void testDifficultySelection() {
        cliHandler = new CLIHandler(parser);

        assertOutputContains("1\nquit\n",
                "=== DIFFICULTY SELECTION ===",
                "1. EASY   (9x9 grid, 10 mines)",
                "2. MEDIUM (16x16 grid, 40 mines)",
                "3. HARD   (16x30 grid, 99 mines)",
                "Selected: EASY difficulty"
        );
    }

    @Test
    void testGameLoopInteractions() {
        when(mockGame.getRevealed()).thenReturn(1);
        when(mockGame.getGameOver()).thenReturn(false).thenReturn(true);
        when(mockGame.getGameStatus()).thenReturn(GameStatus.WON);
        when(mockGame.getElapsedTime()).thenReturn(30000L);

        provideInput("reveal 3,4\nno\n");
        cliHandler.setScanner(new Scanner(System.in));
        cliHandler.run();

        assertAll("Game Loop Interactions",
                () -> verify(mockGame).revealCell(new Position(3, 4)),
                () -> verify(mockGame, atLeastOnce()).getGameOver(),
                () -> verify(mockGame).getGameStatus()
        );
    }

    @Test
    void testDisplayFormatting() {
        when(mockGame.getRevealed()).thenReturn(1);
        when(mockGame.getGameOver()).thenReturn(false);
        when(mockGame.getFlagsPlaced()).thenReturn(2);
        when(mockGame.getElapsedTime()).thenReturn(45000L);
        when(mockGame.getTotalOfNonMineCells()).thenReturn(90);

        MineField mockMineField = mock(MineField.class);
        when(mockMineField.getWidth()).thenReturn(10);
        when(mockMineField.getHeight()).thenReturn(10);
        when(mockGame.getMinefield()).thenReturn(mockMineField);

        Cell mockCell = mock(Cell.class);
        when(mockCell.isRevealed()).thenReturn(false);
        when(mockCell.isFlagged()).thenReturn(false);
        when(mockCell.isMined()).thenReturn(false);
        when(mockMineField.getCell(any(Position.class))).thenReturn(mockCell);
        when(mockMineField.countAdjacentMines(any(Position.class))).thenReturn(0);

        provideInput("quit\n");
        cliHandler.setScanner(new Scanner(System.in));
        cliHandler.run();

        String output = getOutput();

        String[] expectedDisplayElements = {
                "--- Current Minefield ---",
                " 0  1  2  3  4  5  6  7  8  9",
                "  +",
                " 0|", " 5|", " 9|",
                " - ",
                "Revealed: 1 / 90",
                "Flags: 2 / 10",
                "Time: 45s",
                "-------------------------"
        };

        for (String content : expectedDisplayElements) {
            assertTrue(output.contains(content), "Should contain: " + content);
        }
    }

    @Test
    void testResetCommand() {
        provideInput("reset\nquit\n");
        cliHandler.setScanner(new Scanner(System.in));
        cliHandler.run();

        verify(mockGame).resetGame();

        String output = getOutput();
        assertTrue(output.contains("--- Game Reset ---"),
                "Should display reset message");
    }
}