package io.github.floriangubler.algorithms.sorting;

public class BubbleSort {

    /**
     * Sorts an array in place using bubble sort.
     *
     * @param A the array to sort
     */
    public static void bubbleSort(int[] A) {
        // Loop through the array n-1 times
        for (int i = 0; i < A.length; i++) {
            boolean swapped = false;
            // Loop through the array, ignoring the last i elements which are already sorted
            // INV(i) = The last i elements are sorted and in their final position
            for (int j = 0; j < A.length - i - 1; j++) {
                // Compare each pair of adjacent elements and swap them
                // if they are in the wrong order
                if (A[j] > A[j + 1]) {
                    swap(A, j, j + 1);
                    swapped = true;
                }
            }
            // If no elements were swapped, the array is already sorted
            // and we can exit early
            if (!swapped) {
                return;
            }
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
