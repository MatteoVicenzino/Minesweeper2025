import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ms.Cell;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTests {

    @Test
    void testInitialState() {
        Cell cell = new Cell();
        assertFalse(cell.isBomb());
        assertFalse(cell.isRevealed());
        assertFalse(cell.isFlagged());

    }

    @Test
    void testSetBomb() {
        Cell cell = new Cell();

        cell.setBomb(true);
        assertTrue(cell.isBomb());

        cell.setBomb(false);
        assertFalse(cell.isBomb());
    }
}
