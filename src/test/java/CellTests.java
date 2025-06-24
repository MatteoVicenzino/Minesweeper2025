import ms.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTests {

    private Cell cell;

    @BeforeEach
    void setup() {
        cell = new Cell();
    }

    @Test
    void testNewCellIsNotMinedRevealedNorFlagged() {
        assertFalse(cell.isMined());
        assertFalse(cell.isRevealed());
        assertFalse(cell.isFlagged());

    }

    @Test
    void testSetMinedWithTrueAndFalseUpdatesMinedStatus() {
        cell.setMined(true);
        assertTrue(cell.isMined());

        cell.setMined(false);
        assertFalse(cell.isMined());
    }

    @Test
    void testRevealOnHiddenCellMarksAsRevealed() {
        cell.reveal();
        assertTrue(cell.isRevealed());

        cell.reveal();
        assertTrue(cell.isRevealed());
    }

    @Test
    void testToggleFlagOnHiddenCellMarksAsFlagged() {
        cell.toggleFlag();
        assertTrue(cell.isFlagged());

        cell.toggleFlag();
        assertFalse(cell.isFlagged());
    }

    @Test
    void testMarkAsRevealedOnFlaggedCellNotReveals() {

        cell.toggleFlag();
        assertTrue(cell.isFlagged());
        assertFalse(cell.isRevealed());

        cell.reveal();
        assertFalse(cell.isRevealed());
        assertTrue(cell.isFlagged());
        assertFalse(cell.isRevealed());
    }


    @Test
    void testToggleFlagOnRevealedCellNotFlags() {
        cell.reveal();
        cell.isRevealed();
        assertTrue(cell.isRevealed());
        assertFalse(cell.isFlagged());
        cell.toggleFlag();

        assertFalse(cell.isFlagged());
    }
}
