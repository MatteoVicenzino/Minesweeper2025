import org.junit.jupiter.api.Disabled;
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
        assertEquals(ROWS, minefield.getRows());
        assertEquals(COLS, minefield.getCols());
        assertEquals(MINES, minefield.getMines());
        assertEquals(0, minefield.getRevealed());
        assertEquals(ROWS * COLS, minefield.getUnrevealedCount());
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
}
