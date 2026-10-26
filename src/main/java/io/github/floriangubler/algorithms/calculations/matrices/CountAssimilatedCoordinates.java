package io.github.floriangubler.algorithms.calculations.matrices;

public class CountAssimilatedCoordinates {

    /**
     * Counts the number of elements in the matrix that are assimilated.
     * An element is assimilated if the sum of its neighbors is divisible by the element itself.
     *
     * @param matrix The matrix to count assimilated elements in.
     * @return The number of assimilated elements in the matrix.
     */
    public static int countAssimilated(int[][] matrix) {
        int assimilatedCount = 0;
        // Loop through each element in the matrix
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                int sum = 0;
                // For each element, loop through its neighbors (outer index -1 to 1 and inner index -1 to 1) and sum their values
                for(int k = -1; k <= 1; k++) {
                    for(int l = -1; l <= 1; l++) {
                        // Skip the case for the current element itself
                        if(k != 0 || l != 0) {
                            // Check if the neighbor is within bounds of the matrix, if not, treat it as 0
                            sum += (i + k < 0 ||
                                    i + k >= matrix.length ||
                                    j + l < 0 ||
                                    j + l >= matrix[i].length)
                                    ? 0 : matrix[i + k][j + l];
                        }
                    }
                }
                // Check if the sum of the neighbors is divisible by the current element (definition for assimilated)
                if(sum % matrix[i][j] == 0) {
                    assimilatedCount++;
                }
            }
        }
        return assimilatedCount;
    }
}
