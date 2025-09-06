package at.hochschule.burgenland.bswe.algo;

public class Utils {
    
    /**
     * Prints the given Sudoku board to the console in a formatted manner.
     * Each cell in the Sudoku grid is displayed, with sub-grids separated visually
     * by horizontal and vertical lines. Empty cells are represented by a dot ('.').
     *
     * @param sudoku a 2D integer array representing the Sudoku board.
     *               Each element should be a non-negative integer, where 0
     *               represents an empty cell. The array must be square,
     *               and its size must be a perfect square (e.g., 4x4, 9x9, 16x16).
     */
    public static void printSudoku(int[][] sudoku) {
        if (sudoku == null || sudoku.length == 0) {
            System.out.println("Sudoku is null or empty!");
            return;
        }

        int sudokuSize = sudoku.length;
        int subSudokuSize = (int) Math.sqrt(sudokuSize);

        for (int i = 0; i < sudokuSize; i++) {
            if (i % subSudokuSize == 0 && i != 0) {
                printHorizontalLine(sudokuSize, subSudokuSize);
            }

            for (int j = 0; j < sudokuSize; j++) {
                if (j % subSudokuSize == 0 && j != 0) {
                    System.out.print("| ");
                }
                if (sudoku[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(sudoku[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Prints a horizontal line to separate sections of the Sudoku grid. The length of the line
     * depends on the size of the Sudoku grid and the size of its sub-grids.
     *
     * @param sudokuSize the total size of the Sudoku grid. This represents the number of rows
     *                   or columns in the grid (e.g., 9 for a 9x9 Sudoku).
     * @param subSudokuSize the size of individual sub-grids within the Sudoku grid. This is the
     *                      square root of the sudokuSize (e.g., 3 for a 9x9 Sudoku).
     */
    private static void printHorizontalLine(int sudokuSize, int subSudokuSize) {
        for (int i = 0; i < sudokuSize + subSudokuSize; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Creates an empty Sudoku board of the specified size. The board is represented
     * as a 2D integer array, where all elements are initialized to 0.
     *
     * @param size the size of the Sudoku board to be created. This represents both
     *             the number of rows and columns in the grid (e.g., 9 for a 9x9 Sudoku).
     * @return a 2D integer array representing the empty Sudoku board.
     */
    public static int[][] createEmptySudokuBoard(int size) {
        return new int[size][size];
    }

}
