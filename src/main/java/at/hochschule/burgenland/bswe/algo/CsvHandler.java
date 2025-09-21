package at.hochschule.burgenland.bswe.algo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvHandler {

    /**
     * Reads a CSV file containing a square matrix of integers and converts it into a 2D array of integers.
     * The CSV file must have valid formatting, where each line contains the same number of integers,
     * and the number of lines equals the number of integers in each line.
     *
     * @param filename the name or path of the CSV file to read
     * @return a 2D integer array representing the contents of the square matrix in the CSV file
     * @throws RuntimeException if the file cannot be read or has invalid content (e.g., inconsistent row lengths,
     *                          non-integer values, or an empty file)
     */
    public static int[][] readCsv(String filename) {
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                lines.add(values);
            }

            if (lines.isEmpty()) {
                throw new IOException("CSV file is empty");
            }

            int size = lines.size();
            int[][] sudokuBoard = new int[size][size];

            for (int i = 0; i < size; i++) {
                if (lines.get(i).length != size) {
                    throw new IOException("CSV file has invalid format");
                }
                for (int j = 0; j < size; j++) {
                    try {
                        sudokuBoard[i][j] = Integer.parseInt(lines.get(i)[j]);
                    } catch (NumberFormatException e) {
                        throw new IOException("CSV file has invalid format");
                    }
                }
            }
            return sudokuBoard;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes a 2D integer array, representing a Sudoku board or other matrix, to a CSV file.
     * Each row in the matrix corresponds to a line in the CSV file, with values separated by commas.
     * This method ensures that the output format matches the rectangular structure of the input array.
     *
     * @param sudokuBoard a 2D integer array to be written, where each sub-array represents a row
     * @param filename the name or path of the file where the CSV content will be written
     * @throws RuntimeException if an I/O error occurs during writing
     * @throws UnsupportedOperationException if the method is not fully implemented
     */
    public static void writeCsv(int[][] sudokuBoard, String filename) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filename))) {
            for (int i = 0; i < sudokuBoard.length; i++) {
                for (int j = 0; j < sudokuBoard.length; j++) {
                    printWriter.print(sudokuBoard[i][j]);
                    if (j < sudokuBoard.length - 1) {
                        printWriter.print(",");
                    }
                }
                if (i < sudokuBoard.length - 1) {
                    printWriter.println();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
