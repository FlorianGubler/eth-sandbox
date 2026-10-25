package io.github.floriangubler.algorithms.searching.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchTest {

    @Test
    void linearSearchReturnsFirstMatchingIndex() {
        int[] numbers = {7, 3, 5, 3, 9};

        assertEquals(1, LinearSearch.linearSearch(numbers, 3));
        assertEquals(4, LinearSearch.linearSearch(numbers, 9));
    }

    @Test
    void linearSearchReturnsMinusOneWhenValueIsMissing() {
        assertEquals(-1, LinearSearch.linearSearch(new int[]{1, 2, 4}, 3));
        assertEquals(-1, LinearSearch.linearSearch(new int[0], 3));
    }

    @Test
    void binarySearchFindsValuesInSortedArray() {
        int[] numbers = {-4, 0, 3, 3, 8, 12};

        assertEquals(0, BinarySearch.binarySearch(numbers, -4));
        assertEquals(5, BinarySearch.binarySearch(numbers, 12));
        int duplicateIndex = BinarySearch.binarySearch(numbers, 3);
        assertEquals(3, numbers[duplicateIndex]);
    }

    @Test
    void binarySearchSupportsInclusiveSubranges() {
        int[] numbers = {9, 1, 3, 5, 7, 11};

        assertEquals(2, BinarySearch.binarySearch(numbers, 3));
        assertEquals(-1, BinarySearch.binarySearch(numbers, 11));
    }

    @Test
    void binarySearchReturnsMinusOneWhenValueIsMissingOrArrayIsEmpty() {
        assertEquals(-1, BinarySearch.binarySearch(new int[]{1, 2, 4}, 3));
        assertEquals(-1, BinarySearch.binarySearch(new int[0], 3));
    }
}
