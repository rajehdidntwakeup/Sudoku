package at.hochschule.burgenland.bswe.algo;

import java.util.Scanner;

public class UiMenu {

    private Scanner scanner;
    private int[][] currentSudoku;
    private String filepath = "src/main/resources/";

    public UiMenu() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu for interacting with the Sudoku application.
     *
     * This method provides an interactive console menu for users
     * to perform various operations on Sudoku boards. Users can select
     * options to load a Sudoku board from a CSV file, manually enter a Sudoku
     * board, solve the current board, display it, validate its solution,
     * save a solved Sudoku to a CSV file, or exit the application.
     *
     * The menu runs in a loop until the user chooses to exit.
     *
     * Error handling is implemented to manage invalid input and unexpected issues
     * during the execution of menu options.
     */
    public void showMenu() {
        boolean exit = false;
         while (!exit) {
             System.out.println("""
                     \n* Welcome To The Sudoku Checker Menu *
                     Chose an option:
                     1. Load from CSV file
                     2. Enter a Sudoku board manually
                     3. Solve the current Sudoku board
                     4. Display the current Sudoku board
                     5. Validate a Sudoku board
                     6. Save Sudoku solution to CSV
                     7. Exit
                     
                     """);

             try {
                 int option = scanner.nextInt();
                 scanner.nextLine();

                 switch (option) {
                     case 1:
                         loadSudokuBoardFromCSV();
                         break;
                     case 2:
                         enterSudokuBoardManually();
                         break;
                     case 3:
                         solveSudokuBoard();
                         break;
                     case 4:
                         displaySudokuBoard();
                         break;
                     case 5:
                         validateSudokuBoard();
                         break;
                     case 6:
                         saveSudokuSolutionToCSV();
                         break;
                     case 7:
                         exit = true;
                         break;
                     default:
                         System.out.println("Invalid option!");
                 }
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input! Please try again!");
             } catch (Exception e) {
                 System.out.println("Error: " + e.getMessage());
             }
         }
    }

    /**
     * Loads a Sudoku board from a CSV file provided by the user and displays it on the console.
     *
     * The method prompts the user to enter the filename of a CSV file. If the input is empty,
     * a default filename ("input.csv") is used. The CSV file is read using the CsvHandler.readCsv
     * method, and the resulting Sudoku board is stored in the `currentSudoku` field.
     * Upon successful loading, the Sudoku board is displayed using the `displaySudokuBoard` method.
     *
     * If an error occurs during file loading, such as the file not found or the content format
     * being invalid, an error message is printed to the console.
     *
     * The method handles basic exceptions related to file operations and user input, ensuring
     * that the program continues running in case of an error.
     */
    private void loadSudokuBoardFromCSV() {
        try {
            System.out.print("Enter CSV filename (default: input.csv): ");
            String filename = scanner.nextLine();
            if (filename.trim().isEmpty()) {
                filename = "input.csv";
            }

            currentSudoku = CsvHandler.readCsv(filename);
            System.out.println("Sudoku Board loaded successfully!");
            displaySudokuBoard();

        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }


    /**
     * Displays the current Sudoku board in the console.
     *
     * This method checks whether the `currentSudoku` field contains a non-null and non-empty Sudoku board.
     * If the board is null or empty, it prints an error message to the console stating "Sudoku Board is null or empty!".
     * Otherwise, it uses the `Utils.printSudoku` method to print the Sudoku board in a formatted structure.
     *
     * The method relies on the `currentSudoku` field, which is expected to be a 2D integer array representing
     * the Sudoku board. The board should conform to the standard Sudoku rules: a square grid where each nested
     * array has the same length as the number of rows.
     */
    private void displaySudokuBoard() {
        if (currentSudoku == null || currentSudoku.length == 0) {
            System.out.println("Sudoku Board is null or empty!");
            return;
        }
        Utils.printSudoku(currentSudoku);
    }

    /**
     * Allows the user to manually input the Sudoku board via the console.
     *
     * This method prompts the user to specify the size of the Sudoku board, which must be
     * a perfect square (e.g., 4, 9, 16, 25). If the input size is invalid, an error message
     * is displayed and the method terminates.
     *
     * Once a valid size is provided, an empty Sudoku board is created using the
     * `Utils.createEmptySudokuBoard` method. The user is then prompted to enter the board
     * row by row, specifying values separated by spaces. A value of 0 is used to represent
     * empty cells. If a row does not contain the expected number of values, the user is
     * prompted to re-enter the row.
     *
     * After successfully entering the board, it is displayed using the `displaySudokuBoard` method.
     * Any invalid input, such as non-numeric values or incorrect formats, is handled with
     * appropriate error messages.
     *
     * Exceptions:
     * - `NumberFormatException`: Caught if the user provides non-numeric input for any Sudoku cell.
     *
     * Preconditions:
     * - The `scanner` field must be initialized to read user input.
     *
     * Postconditions:
     * - The `currentSudoku` field is set with the manually entered Sudoku board if valid input is provided.
     */
    private void enterSudokuBoardManually() {
        try {
            System.out.println("Enter Sudoku board manually size (4, 9, 16, 25, ...): ");
            int sudokuSize = scanner.nextInt();
            scanner.nextLine();

            if (Math.sqrt(sudokuSize) % 1 != 0) {
                System.out.println("The size must be a perfect square!");
                return;
            }

            currentSudoku = Utils.createEmptySudokuBoard(sudokuSize);

            System.out.println("Enter the Sudoku board row by row.");
            System.out.println("Attention: use 0 for empty cells, and separate numbers with spaces.");

            for (int i = 0; i < sudokuSize; i++) {
                System.out.print("Row " + (i + 1) + ": ");
                String[] values = scanner.nextLine().split(" ");
                if (values.length != sudokuSize) {
                    System.out.println("Invalid number of values! Expected: " + sudokuSize);
                    i--; // Retry this row
                    continue;
                }
                for (int j = 0; j < sudokuSize; j++) {
                    currentSudoku[i][j] = Integer.parseInt(values[j]);
                }

                System.out.println("Sudoku Board was entered successfully!");
                displaySudokuBoard();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please try again!");
        }
    }


    /**
     * Solves the current Sudoku board stored in the `currentSudoku` field.
     *
     * This method attempts to solve the Sudoku board in the `currentSudoku` field.
     * It first checks if the field is null or empty. If the board is invalid,
     * the method prints an error message and terminates execution.
     *
     * When the board is valid, the solving process starts, and the time taken
     * to solve the board is measured. The solving process utilizes the
     * `SudokuChecker.checkSudoku` method to compute the solution. If the board
     * is determined to be unsolvable, a corresponding message is displayed.
     *
     * If the Sudoku board is successfully solved, it updates the `currentSudoku`
     * field and displays the solved Sudoku board using the `displaySudokuBoard` method.
     * A success message, including the time taken to solve, is also printed to the console.
     *
     * Preconditions:
     * - The `currentSudoku` field must be initialized to a valid 2D array or null.
     * - The `SudokuChecker.checkSudoku` method should be implemented to handle the solving logic.
     *
     * Postconditions:
     * - The `currentSudoku` field is updated with the solved Sudoku board if solvable.
     * - If the board is unsolvable or invalid, the `currentSudoku` field remains unchanged.
     */
    private void solveSudokuBoard() {
        if (currentSudoku == null || currentSudoku.length == 0) {
            System.out.println("Sudoku Board is null or empty!");
            return;
        }
        System.out.println("Solving Sudoku...");
        long startTime = System.currentTimeMillis();
        currentSudoku = SudokuChecker.checkSudoku(currentSudoku);
        long endTime = System.currentTimeMillis();
        if (currentSudoku == null) {
            System.out.println("Sudoku is not solvable!");
        } else {
            System.out.println("Sudoku was solved successfully, it took " + (endTime - startTime) + " ms.");
            displaySudokuBoard();
        }
    }


    private void validateSudokuBoard() {
        if (currentSudoku == null || currentSudoku.length == 0) {
            System.out.println("Sudoku Board is null or empty!");
            return;
        }
        System.out.println("Validating Sudoku...");
        long startTime = System.currentTimeMillis();
        boolean isValid = SudokuChecker.isSolved(currentSudoku);
        long endTime = System.currentTimeMillis();
        if (isValid) {
            System.out.println("Sudoku is valid, it took " + (endTime - startTime) + " ms.");
        } else {
            System.out.println("Sudoku is invalid!");
        }
    }


    private void saveSudokuSolutionToCSV() {
        if (currentSudoku == null || currentSudoku.length == 0) {
            System.out.println("Sudoku Board is null or empty!");
            return;
        }
        System.out.print("Enter CSV filename (default: output.csv): ");

        String filename = scanner.nextLine();
        if (filename.trim().isEmpty()) {
            filename = filepath  + "output.csv";
        } else if (filename.endsWith(".csv")) {
            filename = filename.substring(0, filename.length() - 4);
        }
        filename += ".csv";
        CsvHandler.writeCsv(currentSudoku, filepath + filename);
        System.out.println("Sudoku solution saved to file " + filename);
    }

}
