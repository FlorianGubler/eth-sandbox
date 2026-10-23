package io.github.floriangubler.algorithms.calculations.matrices;

public class BackSubstitution {

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
}
