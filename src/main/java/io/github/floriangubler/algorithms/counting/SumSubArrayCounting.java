package io.github.floriangubler.algorithms.counting;

public class SumSubArrayCounting {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        int count = countSubarrays(arr.length, 2, 4, arr);
        System.out.println("Number of subarrays with sum: " + count);
    }

    /**
     * Counts the number of subarrays with sum in the range [L, R].
     *
     * @param n the length of the array
     * @param L the lower bound of the sum
     * @param R the upper bound of the sum
     * @param A the input array
     * @return the count of subarrays with sum in [L, R]
     */
    public static int countSubarrays(int n, int L, int R, int[] A) {
        return countSubarraysAtMost(R, A) - countSubarraysAtMost(L - 1, A);
    }

    /**
     * Counts the number of subarrays with sum at most x.
     *
     * @param x the upper bound of the sum
     * @param A the input array
     * @return the count of subarrays with sum at most x
     */
    public static int countSubarraysAtMost(int x, int[] A){
        // The amount of subarray with sum of at most x
        int subArrayCount = 0;
        // Left boundary of the sliding window
        int left = 0;
        // Current sum of the sliding window
        int currentSum = 0;
        // Loop through the array with the right boundary of the sliding window
        for(int right = 0; right <= A.length - 1; right++){
            // For each right boundary, add the current element to the current sum
            currentSum += A[right];
            // If the current sum is greater than x, move the left boundary up so that the current sum is at most x
            while(currentSum > x){
                currentSum -= A[left];
                left++;
            }
            // With the current sum beeing at most x,
            // all subarrays ending at right and starting from left to right are valid
            // The amount of these subarrays is (right - left + 1)
            subArrayCount += (right + 1) - left;
        }
        return subArrayCount;
    }
}
