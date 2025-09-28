import at.hochschule.burgenland.bswe.algo.SudokuChecker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuCheckerTest {

    @Test
    void testCheckSudokuSolvesValid9x9() {
        int[][] puzzle = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        int[][] solved = SudokuChecker.checkSudoku(puzzle);
        assertNotNull(solved, "Solver should find a solution for a valid Sudoku");
        assertTrue(SudokuChecker.isSolved(solved), "Returned grid should be a valid solved Sudoku");
    }

    @Test
    void testCheckSudokuThrowsOnNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> SudokuChecker.checkSudoku(null));
        assertEquals("Sudoku must NOT be null or empty!", ex.getMessage());
    }

    @Test
    void testCheckSudokuThrowsOnEmpty() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> SudokuChecker.checkSudoku(new int[][]{}));
        assertEquals("Sudoku must NOT be null or empty!", ex.getMessage());
    }

    @Test
    void testCheckSudokuThrowsOnNonSquareSize() {
        // 3x3 grid is not a valid Sudoku size because sqrt(3) * sqrt(3) != 3
        int[][] invalid = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> SudokuChecker.checkSudoku(invalid));
        assertEquals("Sudoku must be a square!", ex.getMessage());
    }

    @Test
    void testIsValidOn4x4Grid() {
        int[][] grid = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        int size = 4; int sub = 2;

        // Place 2 at (0,1): allowed (row has 1, different number; column empty; subgrid empty)
        assertTrue(SudokuChecker.isValid(grid, 0, 1, 2, size, sub));

        // Row conflict: placing 1 in same row
        assertFalse(SudokuChecker.isValid(grid, 0, 1, 1, size, sub));

        // Column conflict: put a 3 at (2,1) then test placing 3 at (0,1)
        grid[2][1] = 3;
        assertFalse(SudokuChecker.isValid(grid, 0, 1, 3, size, sub));

        // Subgrid conflict: put a 4 at (1,0) which shares the top-left 2x2 box with (0,1)
        grid[1][0] = 4;
        assertFalse(SudokuChecker.isValid(grid, 0, 1, 4, size, sub));
    }

    @Test
    void testIsSolvedTrueOn4x4() {
        int[][] solved4x4 = {
                {1, 2, 3, 4},
                {3, 4, 1, 2},
                {2, 1, 4, 3},
                {4, 3, 2, 1}
        };
        assertTrue(SudokuChecker.isSolved(solved4x4));
    }

    @Test
    void testIsSolvedFalseWithZero() {
        int[][] grid = {
                {1, 2, 3, 4},
                {3, 4, 1, 2},
                {2, 1, 0, 3}, // zero present
                {4, 3, 2, 1}
        };
        assertFalse(SudokuChecker.isSolved(grid));
    }

    @Test
    void testIsSolvedFalseWithInvalidDuplicate() {
        int[][] grid = {
                {1, 2, 3, 4},
                {3, 4, 1, 2},
                {2, 1, 4, 3},
                {4, 3, 2, 1}
        };
        // Introduce a duplicate in first row
        grid[0][0] = 2;
        assertFalse(SudokuChecker.isSolved(grid));
    }
}
