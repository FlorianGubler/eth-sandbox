package io.github.floriangubler.algorithms.sorting;

public class InsertionSort {

    /**
     * Sorts the inclusive range of an array in place using insertion sort with binary search.
     *
     * @param A the array to sort
     */
    public static void insertionSort(int[] A) {
        // Loop through each element in the array
        for (int i = 1; i < A.length; i++) {
            // Store the current value to be inserted
            int value = A[i];
            // Find the insertion point for the current value
            // in the sorted prefix of the array
            int insertionPoint = findInsertionPoint(A, 0, i, value);
            // Shift all elements from the insertion point up to the current index
            // one position to the right to make space for the current value
            // We do this in reverse order to avoid overwriting elements
            for (int j = i; j > insertionPoint; j--) {
                A[j] = A[j - 1];
            }
            // Insert the current value at the found insertion point
            A[insertionPoint] = value;
        }
    }

    /**
     * Returns the first position in the sorted prefix where {@code value} can be inserted.
     * The search range is {@code [left, end)}.
     * This is basically a binary search that returns the index of the
     * first element that is greater than or equal to {@code value}.
     *
     * @param A the array to search
     * @param left the left index of the range to search
     * @param right the right index of the range to search
     * @param value the value to search for
     * @return the first position in the sorted prefix where {@code value} can be inserted
     */
    private static int findInsertionPoint(int[] A, int left, int right, int value) {
        // Use a sliding window to search for the insertion point in the array
        while (left < right) {
            // Calculate the middle index of the current search range
            int middle = left + (right - left) / 2;
            // Compare the middle element with the target value
            if (A[middle] < value) {
                left = middle + 1;
            // If the middle element is greater than or equal to the target value,
            // search in the left half
            } else {
                right = middle;
            }
        }
        // Return the left index, which is the first
        // position in the sorted prefix where the
        // given value can be inserted
        return left;
    }
}
