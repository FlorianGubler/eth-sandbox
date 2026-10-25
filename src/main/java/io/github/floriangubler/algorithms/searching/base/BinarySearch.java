package io.github.floriangubler.algorithms.searching.base;

public class BinarySearch {

    /**
     * Searches the inclusive range of a sorted array using binary search.
     *
     * @param A     a sorted array
     * @param value the value to search for
     * @return an index containing {@code value}, or {@code -1} if absent
     */
    public static int binarySearch(int[] A, int value) {
        int left = 0;
        int right = A.length - 1;
        // Use a sliding window to search for the value in the array
        // The loop continues until the left index exceeds the right index,
        // indicating that the value is not present in the array
        while (left <= right) {
            // Calculate the middle index of the current search range
            int middle = left + (right - left) / 2;
            // Compare the middle element with the target value
            if (A[middle] == value) {
                return middle;
            }
            // If the middle element is less than the target value, search in the right half
            if (A[middle] < value) {
                left = middle + 1;
            // If the middle element is greater than the target value, search in the left half
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }
}
