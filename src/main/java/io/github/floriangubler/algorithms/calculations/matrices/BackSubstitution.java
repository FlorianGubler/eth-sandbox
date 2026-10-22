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
        for(int i = U.length - 1; i >= 0; i--) {
            double sum = 0;
            for(int j = i + 1; j < U.length; j++) {
                sum += U[i][j] * x[j];
            }
            x[i] = (c[i] - sum) / U[i][i];
        }
        return x;
    }
}
