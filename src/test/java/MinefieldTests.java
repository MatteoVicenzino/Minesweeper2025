import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ms.MineField;
import static org.junit.jupiter.api.Assertions.*;

public class MinefieldTests {

    private MineField minefield;
    private final int ROWS = 10;
    private final int COLS = 10;
    private final int MINES = 10;

    @BeforeEach
    void setup() {
        minefield = new MineField(ROWS, COLS, MINES);
    }

    @Test
    void testInitialState() {
        assertEquals(ROWS, minefield.getHeight());
        assertEquals(COLS, minefield.getWidth());
        assertEquals(MINES, minefield.getMines());
        // assertEquals(0, minefield.getRevealed());    // moved to game
        // assertEquals(ROWS * COLS, minefield.getUnrevealedCount()); // moved to game
    }

    @Test
    void testInitializeGrid() {

        minefield.initializeGrid(5, 5);

        int mineCount = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (minefield.getCell(r, c).isMined()) {
                    mineCount++;
                }
            }
        }
        assertEquals(MINES, mineCount);

        assertFalse(minefield.getCell(5, 5).isMined());
    }

    @Test
    void testIsValid() {
        assertTrue(minefield.isValid(0, 0));
        assertTrue(minefield.isValid(ROWS-1, COLS-1));
        assertFalse(minefield.isValid(-1, 0));
        assertFalse(minefield.isValid(0, -1));
        assertFalse(minefield.isValid(ROWS, 0));
        assertFalse(minefield.isValid(0, COLS));
    }

    @Test
    void testCountAdjacentMines() {
        boolean[][] minePattern = new boolean[][] {
                {true, true, false, false},
                {false, false, false, false},
                {false, false, true, false},
                {false, false, true, true}
        };
        minefield = new MineField(4, 4, 0);
        // Set mines according to our pattern
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (minePattern[row][col]) {
                    minefield.getCell(row, col).setMined(true);
                }
            }
        }
        assertEquals(0, minefield.countAdjacentMines(3, 0));

        // Test cell with different adjacent mines
        assertEquals(1, minefield.countAdjacentMines(0, 2));
        assertEquals(2, minefield.countAdjacentMines(1, 0));
        assertEquals(2, minefield.countAdjacentMines(2, 1));
        assertEquals(3, minefield.countAdjacentMines(1, 1));

        // Test a corner cell
        assertEquals(1, minefield.countAdjacentMines(0, 0));

        // Test a cell that contains a mine itself - should still count adjacent mines but not itself
        assertEquals(2, minefield.countAdjacentMines(2, 2));
    }

    @Test
    void testCountAdjacentMinesOutOfBounds() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(-1, 0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(0, -1);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(ROWS, 0);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(0, COLS);
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(ROWS, COLS);
        });
    }

}
