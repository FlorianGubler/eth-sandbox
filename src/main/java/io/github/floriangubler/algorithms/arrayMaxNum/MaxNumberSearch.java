package io.github.floriangubler.algorithms.arrayMaxNum;

import java.util.Arrays;

public class MaxNumberSearch {
    public static void main(String[] args) {
        int result = findMax(new int[]{5, 0, 4, 10, 20, 15});
        System.out.println("Result: " + result);
    }

    public static int findMax(int[] numbers) {
        if(numbers.length == 1) {
            return numbers[0];
        } else {
            numbers[numbers.length - 2] = Math.max(numbers[numbers.length - 1], numbers[numbers.length - 2]);
            return findMax(Arrays.copyOf(numbers, numbers.length - 1));
        }
    }
}
