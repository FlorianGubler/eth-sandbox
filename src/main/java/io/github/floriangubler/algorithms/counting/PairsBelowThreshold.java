package io.github.floriangubler.algorithms.counting;

import static io.github.floriangubler.algorithms.sorting.MergeSort.mergeSort;

public class PairsBelowThreshold {

    public static void main(String[] args) {
        int[] A = new int[]{5, 0, 4, 10, 20, 15};
        int threshold = 10;
        int count = countPairs(A.length, threshold, A);
        System.out.println("Number of pairs below threshold " + threshold + ": " + count);
    }

    public static int countPairs(int n, int T, int[] A) { // n = A.length Why a parameter?
        // Sort array
        mergeSort(A, 0, n - 1);
        // Counter for pairs
        int counter = 0;
        // Start a counter from left and one from right
        int left = 0;
        int right = n - 1;
        // Loop while the two counters do not overlap
        while (left != right) {
            // If sum is valid this means every pair with the number in left (inclusivly)
            // up to the number in right is a valid pair
            if(A[left] + A[right] <= T){
                // Increase counter by amount of pairs with left up to right
                counter += right - left;
                // Increase left counter to try with next step
                left++;
                // Right is to big, decrease it and try next combination
            } else {
                right--;
            }
        }
        return counter;
    }
}
