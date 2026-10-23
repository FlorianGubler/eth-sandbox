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
        // This uses that the AInv * A = I, so we can solve for AInv by solving the system of equations
        GaussElimination.GaussResult r = GaussElimination.gaussElimination(A, I);
        if(!r.success) throw new IllegalArgumentException("Singular matrix given (non-invertible)");
        // Create result for inverse matrix
        double[][] AInv = new double[A.length][A[0].length];
        // Solve for each column (vector) of the inverse matrix using back substitution
        for (int j = 0; j < A.length; j++) {
            // Get the j-th column vector from the right hand side of the system of equations
            double[] vC = new double[A.length];
            for (int row = 0; row < A.length; row++) {
                vC[row] = r.C[row][j];
            }
            // Solve for the j-th column of the inverse matrix using back substitution
            double[] solution = BackSubstitution.backSubstitution(r.U, vC);
            // Set the j-th column of the inverse matrix to the solution vector
            for (int row = 0; row < A.length; row++) {
                AInv[row][j] = solution[row];
            }
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
