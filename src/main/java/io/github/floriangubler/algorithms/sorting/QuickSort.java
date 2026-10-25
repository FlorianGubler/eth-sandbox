package io.github.floriangubler.algorithms.sorting;

public class QuickSort {

    /**
     * Sorts an array in place using quick sort.
     *
     * @param A the array to sort
     */
    public static void quickSort(int[] A) {
        quickSort(A, 0, A.length - 1);
    }

    /**
     * Sorts the inclusive range of an array in place using quick sort.
     *
     * @param A the array to sort
     * @param left the left index of the range to sort
     * @param right the right index of the range to sort
     */
    public static void quickSort(int[] A, int left, int right) {
        // Base case: if the range is empty or has one element, it is already sorted
        if (left >= right) {
            return;
        }
        // Partition the array and get the pivot index
        int pivotIndex = partition(A, left, right);
        // Recursively sort the left and right subarrays (pivot is excluded as already in the correct position)
        quickSort(A, left, pivotIndex - 1);
        quickSort(A, pivotIndex + 1, right);
    }

    /**
     * Partitions the array around a pivot element.
     *
     * @param A the array to partition
     * @param left the left index of the range to partition
     * @param right the right index of the range to partition
     * @return the index of the pivot element after partitioning
     */
    private static int partition(int[] A, int left, int right) {
        int originalLeft = left;
        int originalRight = right;
        // We choose the rightmost element as the pivot
        int pivot = A[originalRight];
        // Loop through the range in the array until the left and right pointers meet
        while (left < right) {
            // We move the left pointer up until we find an element that is greater than or equal to the pivot
            while (A[left] <= pivot && left < originalRight) {
                left++;
            }
            // We move the right pointer down until we find an element that is less than or equal to the pivot
            while (A[right] >= pivot && right > originalLeft) {
                right--;
            }
            // If the left pointer is still to the left of the right pointer, we swap the elements at these pointers
            if (left < right) {
                swap(A, left, right);
            }
        }
        // Finally, we swap the pivot element with the element at the left pointer, which is now in the correct position
        swap(A, left, originalRight);
        return left;
    }

    /**
     * Swaps two elements in an array.
     *
     * @param A the array
     * @param first the index of the first element to swap
     * @param second the index of the second element to swap
     */
    private static void swap(int[] A, int first, int second) {
        int value = A[first];
        A[first] = A[second];
        A[second] = value;
    }
}