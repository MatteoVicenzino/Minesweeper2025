import ms.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ms.MineField;
import static org.junit.jupiter.api.Assertions.*;

public class MinefieldTests {

    private MineField minefield;
    private final int ROWS = 10;
    private final int COLS = 10;
    private final int MINES = 10;
    private final int FIRST_ROW = 0;
    private final int FIRST_COL = 0;


    @Test
    void testInitialState() {
        minefield = new MineField(ROWS, COLS, MINES);

        assertEquals(ROWS, minefield.getHeight());
        assertEquals(COLS, minefield.getWidth());
        assertEquals(MINES, minefield.getMines());
    }

    @Test
    void testInitializeGrid() {
        // random, but initial cell have to be empty
        minefield = new MineField(ROWS, COLS, MINES);
        minefield.initializeGrid(new Position(5,5));

        int mineCount = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (minefield.getCell(new Position(r, c)).isMined()) {
                    mineCount++;
                }
            }
        }
        assertEquals(MINES, mineCount);

        assertFalse(minefield.getCell(new Position(5, 5)).isMined());
    }

    @Test
    void testIsValid() {
        minefield = new MineField(ROWS, COLS, MINES);
        minefield.initializeGrid(new Position(5,5));

        assertTrue(minefield.isValid(new Position(0, 0)));
        assertTrue(minefield.isValid(new Position(ROWS-1, COLS-1)));
        assertFalse(minefield.isValid(new Position(-1, 0)));
        assertFalse(minefield.isValid(new Position(0, -1)));
        assertFalse(minefield.isValid(new Position(ROWS, 0)));
        assertFalse(minefield.isValid(new Position(0, COLS)));
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
        minefield.initializeGrid(new Position(-1,-1));
        // Set mines manually according to our pattern
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (minePattern[row][col]) {
                    minefield.getCell(new Position(row, col)).setMined(true);
                }
            }
        }

        assertEquals(0, minefield.countAdjacentMines(new Position(3, 0)));

        // Test cell with different adjacent mines
        assertEquals(1, minefield.countAdjacentMines(new Position(0, 2)));
        assertEquals(2, minefield.countAdjacentMines(new Position(1, 0)));
        assertEquals(2, minefield.countAdjacentMines(new Position(2, 1)));
        assertEquals(3, minefield.countAdjacentMines(new Position(1, 1)));

        // Test a corner cell
        assertEquals(1, minefield.countAdjacentMines(new Position(0, 0)));

        // Test a cell that contains a mine itself - should still count adjacent mines but not itself
        assertEquals(2, minefield.countAdjacentMines(new Position(2, 2)));
    }

    @Test
    void testCountAdjacentMinesOutOfBounds() {
        minefield = new MineField(ROWS, COLS, MINES);
        minefield.initializeGrid(new Position(5,5));

        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(new Position(-1, 0));
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(new Position(0, -1));
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(new Position(ROWS, 0));
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(new Position(0, COLS));
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            minefield.countAdjacentMines(new Position(ROWS, COLS));
        });
    }

}
