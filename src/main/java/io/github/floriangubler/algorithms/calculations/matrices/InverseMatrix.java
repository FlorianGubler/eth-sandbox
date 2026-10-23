package io.github.floriangubler.algorithms.calculations.matrices;

public class InverseMatrix {

    /**
     * Calculates the inverse of a given matrix A.
     *
     * @param A the matrix to invert
     * @return the inverse of the matrix A
     * @throws IllegalArgumentException if the matrix is singular (non-invertible)
     */
    public static double[][] inverseMatrix(double[][] A){
        // Create an identity matrix of the given dimensions
        double[][] I = createIdentityMatrix(A.length, A[0].length);
        // Get upper triangular matrix for A and I and validate result
        GaussElimination.GaussResult r = GaussElimination.gaussElimination(A, I);
        if(!r.success) throw new IllegalArgumentException("Singular matrix given (non-invertible)");
        // Create result for inverse matrix
        double[][] AInv = new double[A.length][A[0].length];
        // Loop trough inverse matrix columns and calculate using back substitution for each
        for(int i = 0; i < A.length; i++){
            AInv[i] = BackSubstitution.backSubstitution(r.U, r.C[i]);
        }
        return AInv;
    }

    /**
     * Creates an identity matrix of the given dimensions.
     *
     * @param m the number of rows
     * @param n the number of columns
     * @return the identity matrix
     */
    private static double[][] createIdentityMatrix(int m, int n) {
        double[][] result = new double[m][n];
        // Loop trough dimensions and set diagonal elements to 1, others to 0
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(i == j){
                    result[i][j] = 1;
                } else {
                    result[i][j] = 0;
                }
            }
        }
        return result;
    }
}
