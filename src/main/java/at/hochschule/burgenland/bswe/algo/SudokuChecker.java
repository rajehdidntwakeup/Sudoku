package at.hochschule.burgenland.bswe.algo;

public class SudokuChecker {

    /**
     * Validates and attempts to solve a given Sudoku puzzle using a backtracking algorithm.
     * Verifies the input Sudoku grid is a valid square matrix and solves the puzzle only
     * if it meets the structural requirements of a Sudoku grid.
     *
     * @param sudoku the 2D integer array representing the Sudoku puzzle to be solved. Elements
     *               in the grid must be non-negative integers, where 0 represents an empty cell.
     *               The size of the grid must be n x n, where n is a perfect square (e.g., 4x4, 9x9).
     * @return the solved Sudoku grid if a solution exists, or null if the puzzle cannot be solved.
     * @throws IllegalArgumentException if the provided Sudoku grid is null, empty, or not a
     *                                  square grid with valid dimensions.
     */
    public static int[][] checkSudoku(int[][] sudoku) {
        if (sudoku == null || sudoku.length == 0) {
            throw new IllegalArgumentException("Sudoku must NOT be null or empty!");
        }

        int sudokuSize = sudoku.length;
        int subSudokuSize = (int) Math.sqrt(sudokuSize);

        if (subSudokuSize * subSudokuSize != sudokuSize) {
            throw new IllegalArgumentException("Sudoku must be a square!");
        }

        if (solveSudokuWithBacktrackingAlgorithm(sudoku, sudokuSize, subSudokuSize)) {
            return sudoku;
        } else {
            return null;
        }
    }

    /**
     * Attempts to solve a Sudoku puzzle using a backtracking algorithm. This method recursively
     * fills in the empty cells (denoted by 0) with valid numbers that comply with Sudoku rules.
     *
     * The algorithm ensures that the number placed in an empty cell follows the constraints:
     * - The number must not appear in the same row, column, or subgrid.
     * - The Sudoku grid must maintain structural integrity throughout the process.
     *
     * @param sudoku the two-dimensional integer array representing the Sudoku grid to be solved.
     *               Empty cells are represented by 0. The grid must be a square matrix of size
     *               sudokuSize x sudokuSize.
     * @param sudokuSize the size of the Sudoku grid (e.g., 9 for a 9x9 grid). This must be a
     *                   perfect square.
     * @param subSudokuSize the size of the subgrid (e.g., 3 for a 3x3 subgrid in a 9x9 grid).
     *                      This value corresponds to the square root of sudokuSize.
     * @return true if the Sudoku puzzle is successfully solved, false if no valid solution exists.
     *         The input array will be directly modified to reflect the solved puzzle if a solution
     *         is found.
     */
    private static boolean solveSudokuWithBacktrackingAlgorithm(int[][] sudoku, int sudokuSize, int subSudokuSize) {
        for (int row = 0; row < sudokuSize; row++) {
            for (int col = 0; col < sudokuSize; col++) {
                if ( sudoku[row][col] == 0) {
                    for (int num = 1; num <= sudokuSize; num++) {
                        if (isValid(sudoku, row, col, num, sudokuSize, subSudokuSize)) {
                            sudoku[row][col] = num;
                            if (solveSudokuWithBacktrackingAlgorithm(sudoku, sudokuSize, subSudokuSize)) {
                                return true;
                            }
                            sudoku[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Checks whether a given number can be placed in a specific cell of a Sudoku grid.
     * Ensures that the number does not conflict with existing numbers in the same row,
     * column, or subgrid.
     *
     * @param sudoku the 2D integer array representing the Sudoku grid.
     * @param row the row index of the cell to check.
     * @param col the column index of the cell to check.
     * @param num the number to validate for placement in the specified cell.
     * @param sudokuSize the size of the Sudoku grid (e.g., 9 for a 9x9 grid). It must be a perfect square.
     * @param subSudokuSize the size of the subgrid (e.g., 3 for a 3x3 subgrid in a 9x9 grid).
     *                      This value corresponds to the square root of sudokuSize.
     * @return true if the number can be placed in the specified cell without violating Sudoku constraints,
     *         false otherwise.
     */
    public static boolean isValid(int[][] sudoku, int row, int col, int num,
                               int sudokuSize, int subSudokuSize) {
        for (int i = 0; i < sudokuSize; i++) {
            if (sudoku[row][i] == num) return false;
        }
        for (int i = 0; i < sudokuSize; i++) {
            if (sudoku[i][col] == num) return false;
        }
        int startRow = (row / subSudokuSize) * subSudokuSize;
        int startCol = (col / subSudokuSize) * subSudokuSize;
        for (int i = startRow; i < startRow + subSudokuSize; i++) {
            for (int j = startCol; j < startCol + subSudokuSize; j++) {
                if (sudoku[i][j] == num) return false;
            }
        }
        return true;
    }

    /**
     * Determines whether a given Sudoku grid is completely solved and valid according to Sudoku rules.
     * A Sudoku grid is considered solved if all cells are filled with valid numbers that
     * comply with the constraints of appearing only once in each row, column, and subgrid.
     *
     * @param sudoku the 2D integer array representing the Sudoku grid to check. Each cell in the grid
     *               must be a non-negative integer, with 0 representing an unfilled cell. The grid
     *               must be square (e.g., 9x9, 4x4) with dimensions that are perfect squares.
     * @return true if the Sudoku grid is filled and all numbers follow the Sudoku rules,
     *         false otherwise.
     */
    public static boolean isSolved(int[][] sudoku) {
        int sudokuSize = sudoku.length;
        int subSudokuSize = (int) Math.sqrt(sudokuSize);
        for (int row = 0; row < sudokuSize; row++) {
            for (int col = 0; col < sudokuSize; col++) {
                if (sudoku[row][col] == 0) return false;
                int temp = sudoku[row][col];
                if (!isValid(sudoku, row, col, temp, sudokuSize, subSudokuSize)) {
                    sudoku[row][col] = temp;
                    return false;
                }
                sudoku[row][col] = temp;
            }
        }
        return true;
    }

}
