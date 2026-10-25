package io.github.floriangubler.algorithms.sorting;

public class SelectionSort {

    /**
     * Sorts the inclusive range of an array in place using selection sort.
     *
     * @param A the array to sort
     */
    public static void selectionSort(int[] A) {
        // Loop through the array from left to right
        for (int i = 0; i < A.length; i++) {
            int maximum = 0;
            // Loop through the array to find the maximum
            // element in the unsorted part of the array
            for (int j = 0; j < A.length - i; j++) {
                if (A[j] > A[maximum]) {
                    maximum = j;
                }
            }
            // Swap the maximum element with the last element of the unsorted part
            swap(A, maximum, A.length - 1 - i);
        }
    }

    /**
     * Swaps two elements in an array in place.
     *
     * @param A the array
     * @param first the index of the first element
     * @param second the index of the second element
     */
    private static void swap(int[] A, int first, int second) {
        int value = A[first];
        A[first] = A[second];
        A[second] = value;
    }
}
