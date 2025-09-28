import at.hochschule.burgenland.bswe.algo.CsvHandler;
import at.hochschule.burgenland.bswe.algo.SudokuChecker;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CsvFileSudokuTest {

    private String resourcePath(String name) {
        try {
            URL url = getClass().getClassLoader().getResource(name);
            assertNotNull(url, "Resource not found: " + name);
            return Paths.get(url.toURI()).toString();
        } catch (URISyntaxException e) {
            fail("Failed to resolve resource path for: " + name + " due to " + e.getMessage());
            return null; // unreachable
        }
    }

    private void assertGridsEqual(int[][] expected, int[][] actual) {
        assertEquals(expected.length, actual.length, "Grid sizes differ");
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i], "Row " + i + " differs");
        }
    }

    @Test
    void testSolveFromCsv_input2_matches_output2() {
        String inputPath = resourcePath("input2.csv");
        String expectedPath = resourcePath("output2.csv");

        int[][] input = CsvHandler.readCsv(inputPath);
        int[][] solved = SudokuChecker.checkSudoku(input);
        assertNotNull(solved, "Solver should produce a solution for input2.csv");
        assertTrue(SudokuChecker.isSolved(solved), "Solved grid should be valid for input2.csv");

        int[][] expected = CsvHandler.readCsv(expectedPath);
        assertGridsEqual(expected, solved);
    }

    @Test
    void testSolveFromCsv_input3_matches_output3() {
        String inputPath = resourcePath("input3.csv");
        String expectedPath = resourcePath("output3.csv");

        int[][] input = CsvHandler.readCsv(inputPath);
        int[][] solved = SudokuChecker.checkSudoku(input);
        assertNotNull(solved, "Solver should produce a solution for input3.csv");
        assertTrue(SudokuChecker.isSolved(solved), "Solved grid should be valid for input3.csv");

        int[][] expected = CsvHandler.readCsv(expectedPath);
        assertGridsEqual(expected, solved);
    }
}
