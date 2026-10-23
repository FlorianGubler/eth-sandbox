package io.github.floriangubler.algorithms.calculations.matrices;

import java.util.Arrays;

public class GaussJordanElimination {

    /**
     * Represents the result of the Gauss-Jordan elimination process,
     * including the reduced row echelon form of the matrix, the modified vector, and the indices of the downward steps taken during the elimination.
     */
    public static class GaussJordanResult {
        double[][] R;
        double [][] C;
        int[] downwardStepIndices;

        GaussJordanResult(double[][] R, double[][] C, int[] downwardStepIndices) {
            this.R = R;
            this.C = C;
            this.downwardStepIndices = downwardStepIndices;
        }

        @Override
        public String toString() {
            return "GaussResult{" +
                    "R=" + Arrays.deepToString(R) +
                    ", C=" + Arrays.deepToString(C) +
                    ", downwardStepIndices=" + Arrays.toString(downwardStepIndices) +
                    '}';
        }
    }

    public static GaussJordanResult gaussJordanElimination(double[][] A, double[][] B) {
        // Copy the input matrix A and vector B to avoid modifying the original data
        double[][] R = Arrays.stream(A).map(double[]::clone).toArray(double[][]::new);
        double[][] C = Arrays.stream(B).map(double[]::clone).toArray(double[][]::new);
        // Initialize tracker for downward step indices
        int[] downwardStepIndices = new int[R.length];
        int r = 0;
        // Loop through each column of the matrix R
        for(int j = 0; j < R[0].length; j++) {
            // If r and equals the number of rows in R
            // all rows are independent and have been
            // processed, break the loop
            if(r == R.length) {
                break; // All rows have been processed
            }

            if(R[r][j] == 0){
                int k = getExchangeRow(r, j, R);
                if(k != -1){
                    exchangeRows(R, r, k);
                    if(r < C.length){
                        exchangeRows(C, r, k);
                    }
                // No downward step in column j, continue to next column
                } else {
                    continue;
                }
            }
            divideRow(R, r, R[r][j]);
            if(r < C.length){
                divideRow(C, r, R[r][j]);
            }
            for(int i = 0; i < R.length; i++){
                if(i != r && R[i][j] != 0){
                    double factor = R[i][j];
                    subtractRows(R, i, r, factor);
                    if(i < C.length){
                        subtractRows(C, i, r, factor);
                    }
                }
            }
            downwardStepIndices[r++] = j;
        }
        return new GaussJordanResult(R, C, Arrays.copyOf(downwardStepIndices, r));
    }

    /**
     * Subtracts a multiple of one row from another row in a given matrix.
     *
     * @param X The matrix in which the rows will be modified.
     * @param i The index of the row to be modified.
     * @param j The index of the row to be subtracted from.
     * @param factor The factor by which to multiply row j before subtracting it from row i.
     */
    private static void subtractRows(double[][] X, int i, int j, double factor) {
        // Loop through each element in the row and subtract the corresponding element from row j multiplied by the factor
        for(int k = 0; k < X[i].length; k++){
            X[i][k] -= factor * X[j][k];
        }
    }

    /**
     * Divides a row in a given matrix by a specified factor.
     *
     * @param X The matrix in which the row will be modified.
     * @param i The index of the row to be divided.
     * @param factor The factor by which to divide each element of the row.
     */
    private static void divideRow(double[][] X, int i, double factor) {
        for(int k = 0; k < X[i].length; k++){
            X[i][k] /= factor;
        }
    }

    /**
     * Finds a row below the given row index r that has a non-zero entry in column index c.
     *
     * @param r The starting row index to check for non-zero entries.
     * @param c The column index to check for non-zero entries.
     * @param X The matrix to search for a suitable row.
     * @return The index of the row with a non-zero entry in column j, or -1 if no such row exists.
     */
    private static int getExchangeRow(int r, int c, double[][] X) {
        int exchangeRow = -1;
        for(int i = r + 1; i < X.length; i++) {
            if (X[i][c] != 0) {
                exchangeRow = i;
                break;
            }
        }
        return exchangeRow;
    }

    /**
     * Exchanges two rows in a given matrix.
     *
     * @param X The matrix in which the rows will be exchanged.
     * @param r1 The index of the first row to be exchanged.
     * @param r2 The index of the second row to be exchanged.
     */
    private static void exchangeRows(double[][] X, int r1, int r2) {
        double[] tmpRowA = X[r1];
        X[r1] = X[r2];
        X[r2] = tmpRowA;
    }
}
