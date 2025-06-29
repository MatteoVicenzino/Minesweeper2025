package model;

import ms.model.GridDimension;
import ms.model.MineField;
import ms.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinefieldTest {

    private final GridDimension DIMENSIONS = new GridDimension(10, 10);
    private final int ROWS = DIMENSIONS.height();
    private final int COLS = DIMENSIONS.width();
    private final int MINES = 10;
    private MineField minefield;

    @Test
    void testNewMinefieldHasCorrectDimensionsAndMineCount() {
        minefield = new MineField(DIMENSIONS, MINES);

        assertEquals(ROWS, minefield.getHeight());
        assertEquals(COLS, minefield.getWidth());
        assertEquals(MINES, minefield.getMines());
    }

    @Test
    void testInitializeGridPlacesCorrectMineCountAndExcludesFirstClickPosition() {

        minefield = new MineField(DIMENSIONS, MINES);
        minefield.initializeGrid(new Position(5, 5));

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
    void testIsValidPositionWithVariousCoordinatesReturnsCorrectBooleans() {
        minefield = new MineField(DIMENSIONS, MINES);
        minefield.initializeGrid(new Position(5, 5));

        assertTrue(DIMENSIONS.isValidPosition(new Position(0, 0)));
        assertTrue(DIMENSIONS.isValidPosition(new Position(ROWS - 1, COLS - 1)));
        assertFalse(DIMENSIONS.isValidPosition(new Position(-1, 0)));
        assertFalse(DIMENSIONS.isValidPosition(new Position(0, -1)));
        assertFalse(DIMENSIONS.isValidPosition(new Position(ROWS, 0)));
        assertFalse(DIMENSIONS.isValidPosition(new Position(0, COLS)));
    }

    @Test
    void testCountAdjacentMinesWithDifferentMinePatternsReturnsCorrectCounts() {
        boolean[][] minePattern = new boolean[][]{
                {true, true, false, false},
                {false, false, false, false},
                {false, false, true, false},
                {false, false, true, true}
        };
        minefield = new MineField(DIMENSIONS, 0);
        minefield.initializeGrid(new Position(1, 1));

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                if (minePattern[row][col]) {
                    minefield.getCell(new Position(row, col)).setMined(true);
                }
            }
        }

        assertEquals(0, minefield.countAdjacentMines(new Position(3, 0)));

        assertEquals(1, minefield.countAdjacentMines(new Position(0, 2)));
        assertEquals(2, minefield.countAdjacentMines(new Position(1, 0)));
        assertEquals(2, minefield.countAdjacentMines(new Position(2, 1)));
        assertEquals(3, minefield.countAdjacentMines(new Position(1, 1)));

        assertEquals(1, minefield.countAdjacentMines(new Position(0, 0)));

        assertEquals(2, minefield.countAdjacentMines(new Position(2, 2)));
    }

    @Test
    void testCountAdjacentMinesWithInvalidPositionThrowsIndexOutOfBoundsException() {
        minefield = new MineField(DIMENSIONS, MINES);
        minefield.initializeGrid(new Position(5, 5));

        assertThrows(IndexOutOfBoundsException.class, () -> minefield.countAdjacentMines(new Position(-1, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> minefield.countAdjacentMines(new Position(0, -1)));
        assertThrows(IndexOutOfBoundsException.class, () -> minefield.countAdjacentMines(new Position(ROWS, 0)));
        assertThrows(IndexOutOfBoundsException.class, () -> minefield.countAdjacentMines(new Position(0, COLS)));
        assertThrows(IndexOutOfBoundsException.class, () -> minefield.countAdjacentMines(new Position(ROWS, COLS)));
    }
}
