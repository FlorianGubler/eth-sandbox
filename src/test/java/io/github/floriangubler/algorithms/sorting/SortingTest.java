package io.github.floriangubler.algorithms.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortingTest {

    private static final int[] UNSORTED = {5, -2, 8, 5, 0, 3, -2};
    private static final int[] SORTED = {-2, -2, 0, 3, 5, 5, 8};

    @Test
    void mergeSortSortsArray() {
        int[] numbers = UNSORTED.clone();

        MergeSort.mergeSort(numbers, 0, numbers.length - 1);

        assertArrayEquals(SORTED, numbers);
    }

    @Test
    void mergeSortSortsOnlyRequestedRange() {
        int[] numbers = {10, 5, 4, 3, 2, 20};

        MergeSort.mergeSort(numbers, 1, 4);

        assertArrayEquals(new int[]{10, 2, 3, 4, 5, 20}, numbers);
    }

    @Test
    void bubbleSortSortsArray() {
        int[] numbers = UNSORTED.clone();

        BubbleSort.bubbleSort(numbers);

        assertArrayEquals(SORTED, numbers);
    }

    @Test
    void selectionSortSortsArray() {
        int[] numbers = UNSORTED.clone();

        SelectionSort.selectionSort(numbers);

        assertArrayEquals(SORTED, numbers);
    }

    @Test
    void insertionSortSortsArray() {
        int[] numbers = UNSORTED.clone();

        InsertionSort.insertionSort(numbers);

        assertArrayEquals(SORTED, numbers);
    }

    @Test
    void insertionSortFindsInsertionPointsBeforeAndAfterSortedPrefix() {
        int[] numbers = {3, 1, 2, 5, 4};

        InsertionSort.insertionSort(numbers);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, numbers);
    }

    @Test
    void quickSortSortsArray() {
        int[] numbers = UNSORTED.clone();

        QuickSort.quickSort(numbers);

        assertArrayEquals(SORTED, numbers);
    }

    @Test
    void heapSortSortsArray() {
        int[] numbers = UNSORTED.clone();

        HeapSort.heapSort(numbers);

        assertArrayEquals(SORTED, numbers);
    }

    @Test
    void heapSortFindsInsertionPointsBeforeAndAfterSortedPrefix() {
        int[] numbers = {3, 1, 2, 5, 4};

        HeapSort.heapSort(numbers);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, numbers);
    }

    @Test
    void sortingAlgorithmsHandleEmptyAndSingleElementArrays() {
        int[] empty = {};
        int[] single = {1};

        MergeSort.mergeSort(empty, 0, -1);
        BubbleSort.bubbleSort(empty);
        SelectionSort.selectionSort(empty);
        InsertionSort.insertionSort(empty);
        QuickSort.quickSort(empty);

        MergeSort.mergeSort(single, 0, 0);
        BubbleSort.bubbleSort(single);
        SelectionSort.selectionSort(single);
        InsertionSort.insertionSort(single);
        QuickSort.quickSort(single);

        assertArrayEquals(new int[]{}, empty);
        assertArrayEquals(new int[]{1}, single);
    }
}
