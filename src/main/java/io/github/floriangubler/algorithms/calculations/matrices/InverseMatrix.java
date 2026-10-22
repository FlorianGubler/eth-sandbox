package io.github.floriangubler.algorithms.calculations.matrices;

public class InverseMatrix {

    public static double[][] inverseMatrix(double[][] A){
        // Create an identity matrix of the given dimensions
        double[][] I = createIdentityMatrix(A.length, A[0].length);
        // Get lower triangular matrix for A and I
        GaussElimination.GaussResult r = GaussElimination.gaussElimination(A, I);
        // Check result
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
     *
     * @param m
     * @param n
     * @return
     */
    private static double[][] createIdentityMatrix(int m, int n) {
        double[][] result = new double[m][n];
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
