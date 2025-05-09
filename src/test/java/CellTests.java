import org.junit.jupiter.api.Test;
import ms.Cell;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTests {

    @Test
    void testInitialState() {
        Cell cell = new Cell();
        assertFalse(cell.isMined());
        assertFalse(cell.isRevealed());
        assertFalse(cell.isFlagged());

    }

    @Test
    void testSetMine() {
        Cell cell = new Cell();

        cell.setMined(true);
        assertTrue(cell.isMined());

        cell.setMined(false);
        assertFalse(cell.isMined());
    }

    @Test
    void testRevealCell() {
        Cell cell = new Cell();

        assertTrue(cell.reveal());
        assertTrue(cell.isRevealed());

        assertFalse(cell.reveal());
        assertTrue(cell.isRevealed());
    }

    @Test
    void testToggleFlagCell() {
        Cell cell = new Cell();

        cell.toggleFlag();
        assertTrue(cell.isFlagged());

        cell.toggleFlag();
        assertFalse(cell.isFlagged());
    }

    @Test
    void testToggleFlagOnRevealedCell() {
        Cell cell = new Cell();
        cell.reveal();
        assertTrue(cell.isRevealed());
        assertFalse(cell.isFlagged());
        cell.toggleFlag();

        assertFalse(cell.isFlagged());
    }


}
