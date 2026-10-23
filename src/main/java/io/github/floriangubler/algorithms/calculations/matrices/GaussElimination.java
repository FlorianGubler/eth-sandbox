package io.github.floriangubler.algorithms.calculations.matrices;

import java.util.Arrays;

public class GaussElimination {

    /**
     * A class to hold the result of the Gaussian elimination process.
     * It contains the upper triangular matrix U, the modified vector C, and a success flag indicating whether the elimination was successful.
     */
    public static class GaussResult {
        double[][] U;
        double [][] C;
        boolean success;

        GaussResult(double[][] U, double[][] C, boolean success) {
            this.U = U;
            this.C = C;
            this.success = success;
        }

        @Override
        public String toString() {
            return "GaussResult{" +
                    "U=" + Arrays.deepToString(U) +
                    ", C=" + Arrays.deepToString(C) +
                    ", success=" + success +
                    '}';
        }
    }

    /**
     * Performs Gaussian elimination on the given matrix A and list of vectors B
     * to transform A into an upper triangular matrix U and modify B accordingly.
     *
     * @param A The coefficient matrix to be modified into an upper triangular form.
     * @param B The matrix as list of column vectors to be modified according to the transformations applied to A.
     * @return A GaussResult object containing the upper triangular matrix U and the modified matrix C containing the transformed column vectors.
     * @throws IllegalArgumentException if the matrix A is singular and cannot be solved using Gaussian elimination.
     */
    public static GaussResult gaussElimination(double[][] A, double[][] B) {
        // Copy the input matrix A and vector B to avoid modifying the original data
        double[][] U = Arrays.stream(A).map(double[]::clone).toArray(double[][]::new);
        double[][] C = Arrays.stream(B).map(double[]::clone).toArray(double[][]::new);
        // Loop through each column of the matrix U
        for(int j = 0; j < U.length; j++) {
            // If the current pivot element we need to exchange the current row
            // with a row below that has a non-zero entry in the same column
            if(U[j][j] == 0){
                // Try finding the exchange row
                int exchangeRow = getExchangeRow(j, j, U);
                // If we found a row to exchange with, perform the exchange on both U and all vectors in C
                if(exchangeRow != -1){
                    exchangeRows(U, j, exchangeRow);
                    if(j < C.length){
                        exchangeRows(C, j, exchangeRow);
                    }
                // If there is no such row, the matrix is singular,
                // and we cannot proceed with Gaussian elimination
                } else {
                    return new GaussResult(U, C, false);
                }
            }
            // Loop through the rows below the current row to eliminate the entries in the current column
            for(int i = j + 1; i < U.length; i++){
                // Calculate the factor by which to multiply the current row before subtracting it from the row below
                double f = U[i][j] / U[j][j];
                // Subtract the current row multiplied by the factor from the row below in both U and all vectors in C
                subtractRows(U, i, j, f);
                if(i < C.length){
                    subtractRows(C, i, j, f);
                }
            }
        }
        return new GaussResult(U, C, true);
    }

    /**
     * Performs back substitution on an upper triangular matrix U and a vector c
     * to find the solution vector x.
     *
     * @param U The upper triangular matrix.
     * @param c The vector corresponding to the right-hand side of the equation Ux = c.
     * @return The solution vector x.
     */
    public static double[] backSubstitution(double[][] U, double[] c) {
        double[] x = new double[U.length];
        // Loop from last equation to first
        for(int i = U.length - 1; i >= 0; i--) {
            double sum = 0;
            // For each matrix calculate sum of known values
            // (lower coordinates of x already solved) with their
            // factors in the current equation
            for(int j = i + 1; j < U.length; j++) {
                sum += U[i][j] * x[j];
            }
            // Current coordinate of x is calculated by subtracting
            // the sum from the right-hand side value and dividing
            // by the factor of the current coordinate in the upper triangular matrix
            x[i] = (c[i] - sum) / U[i][i];
        }
        return x;
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
