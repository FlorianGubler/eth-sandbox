package io.github.floriangubler.algorithms.sorting;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args){
        int[] A = new int[]{5, 0, 4, 10, 20, 15};
        mergeSort(A, 0, A.length - 1);
        System.out.println(Arrays.toString(A));
    }

    /**
     * Merge sort algorithm. Sorts an array in place using the merge sort algorithm.
     *
     * @param A The array to be sorted
     * @param left The index of the left boundary of the part to be sorted (included)
     * @param right The index of the right boundary of the part to be sorted (included)
     */
    public static void mergeSort(int[] A, int left, int right){
        if(left < right){
            int middle = (left + right) / 2; // Rounded down
            // Sort left part
            mergeSort(A, left, middle);
            // Sort right part
            mergeSort(A, middle + 1, right);
            // Merge the two parts efficiently
            merge(A, left, middle, right);
        }
    }

    /**
     * Merging function for the merge sort algorithm. Takes an array and boundaries
     * for two sorted parts next to each other (e.g. [x, x, left, x, middle, x, right, x, x])
     * and merges them sorted
     *
     * @param A The array to be sorted
     * @param left The index of the left boundary from the merge part (included)
     * @param middle The middle between the two parts to be merged
     * @param right The index of the right boundary from the merge part (included)
     */
    public static void merge(int[] A, int left, int middle, int right){
        // Start an indexCounter for the left and right part we want to merge
        int indexLeft = left;
        int indexRight = middle + 1;
        // Create a copyArr we can manipulate
        int[] copyArr = new int[(right + 1) - left];
        // Create an index counter for the copy array
        int indexCopyArr = 0;
        //
        while(indexLeft <= middle && indexRight <= right){
            if(A[indexLeft] < A[indexRight]){
                copyArr[indexCopyArr] = A[indexLeft];
                indexLeft++;
            } else {
                copyArr[indexCopyArr] = A[indexRight];
                indexRight++;
            }
            indexCopyArr++;
        }
        while (indexLeft <= middle) {
            copyArr[indexCopyArr++] = A[indexLeft++];
        }
        while (indexRight <= right) {
            copyArr[indexCopyArr++] = A[indexRight++];
        }
        System.arraycopy(copyArr, 0, A, left, copyArr.length);
    }
}
