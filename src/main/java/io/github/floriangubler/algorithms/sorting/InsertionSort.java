package io.github.floriangubler.algorithms.sorting;

public class InsertionSort {

    public static void insertionSort(int[] A) {
        insertionSort(A, 0, A.length - 1);
    }

    /**
     * Sorts the inclusive range of an array in place using insertion sort.
     */
    public static void insertionSort(int[] A, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int value = A[i];
            int j = i - 1;
            while (j >= left && A[j] > value) {
                A[j + 1] = A[j];
                j--;
            }
            A[j + 1] = value;
        }
    }
}
