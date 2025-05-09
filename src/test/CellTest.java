import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CellTests {

    @Test
    void testInitialState() {
        Cell cell = new Cell();
        assertFalse(cell.isBomb());
        assertFalse(cell.isRevealed());
        assertFalse(cell.isFlagged());

    }

}