package io.github.floriangubler.algorithms.sorting;

public class HeapSort {

    /**
     * Sorts the given array using the heapsort algorithm.
     *
     * @param A the array to be sorted
     */
    public static void heapSort(int[] A) {
        // Build the initial heap from the array by looping
        // through the array starting from the last non-leaf
        // node down to the root node (Leafs do not need
        // to be "heapified" as they do not have children
        for(int i = (A.length - 1) / 2; i >= 0; i--) {
            restoreHeapCondition(A, i, A.length - 1);
        }
        // Loop through the array from the last element down to the second element,
        // switching the root of the heap with the last element
        // and then restoring the heap condition for the remaining elements
        for(int i = A.length - 1; i >= 1; i--) {
            switchElements(A, 0, i);
            restoreHeapCondition(A, 0, i - 1);
        }
    }

    /**
     * Switches the elements at the given indices in the array.
     *
     * @param A the array
     * @param i1 the index of the first element (1-based)
     * @param i2 the index of the second element (1-based)
     */
    private static void switchElements(int[] A, int i1, int i2){
        int temp = A[i1];
        A[i1] = A[i2];
        A[i2] = temp;
    }

    /**
     * Restores the heap condition in the array from the given start index to the end index.
     *
     * @param A the array
     * @param start the starting index (1-based)
     * @param end the ending index (1-based)
     */
    private static void restoreHeapCondition(int[] A, int start, int end) {
        // Start from the current index and move down the heap
        int current = start;
        while (true) {
            // Get the indices of the left and right children
            int leftChildIndex = 2 * current + 1;
            int rightChildIndex = 2 * current + 2;
            // Assume the current index is the largest
            int largest = current;
            // Compare the current element with its children and find the largest
            if (leftChildIndex <= end && A[leftChildIndex] > A[largest]) {
                largest = leftChildIndex;
            }
            // Compare the current element with its children and find the largest
            if (rightChildIndex <= end && A[rightChildIndex] > A[largest]) {
                largest = rightChildIndex;
            }
            // If the largest element is still the current element,
            // the heap condition is satisfied
            if (largest == current) {
                break;
            }
            // If heap condition is not satisfied, switch the current
            // element with the largest child and continue down the heap
            switchElements(A, current, largest);
            current = largest;
        }
    }
}
