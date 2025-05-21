import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ms.Cell;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTests {

    private Cell cell;

    @BeforeEach
    void setup() {
        cell = new Cell();
    }

    @Test
    void testInitialState() {
        assertFalse(cell.isMined());
        assertFalse(cell.isRevealed());
        assertFalse(cell.isFlagged());

    }

    @Test
    void testSetMine() {
        cell.setMined(true);
        assertTrue(cell.isMined());

        cell.setMined(false);
        assertFalse(cell.isMined());
    }

    @Test
    void testRevealCell() {
        assertTrue(cell.reveal());
        assertTrue(cell.isRevealed());

        assertFalse(cell.reveal());
        assertTrue(cell.isRevealed());
    }

    @Test
    void testToggleFlagCell() {
        cell.toggleFlag();
        assertTrue(cell.isFlagged());

        cell.toggleFlag();
        assertFalse(cell.isFlagged());
    }

    @Test
    void testToggleFlagOnRevealedCell() {
        cell.reveal();
        assertTrue(cell.isRevealed());
        assertFalse(cell.isFlagged());
        cell.toggleFlag();

        assertFalse(cell.isFlagged());
    }
}
