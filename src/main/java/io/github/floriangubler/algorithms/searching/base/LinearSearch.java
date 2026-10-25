package io.github.floriangubler.algorithms.searching.base;

public class LinearSearch {

    /**
     * Searches an array from left to right and returns the first matching index.
     *
     * @param A the array to search
     * @param value the value to search for
     * @return the first index containing {@code value}, or {@code -1} if absent
     */
    public static int linearSearch(int[] A, int value) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
