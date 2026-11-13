package io.github.floriangubler.algorithms.knapsack;

public class KnapsackProblems {

    /**
     * Given a list of friends (length n) and the amount of coffee (A) and tea (B) they drink, find the maximum amount of friends to invite
     * to a party with k coffees and k teas available.
     * As a subproblem we use the maximum amount of friends to invite for a given amount of coffees and teas available.
     * Runs in O(n * k^2) time and O(k^2) space.
     *
     * @param n Amount of friends
     * @param k Amount of coffees / teas available
     * @param A Amount of coffee each friend drinks
     * @param B Amount of tea each friend drinks
     * @return Maximum amount of friends to invite
     */
    public static int maxFriends(int n, int k, int[] A, int[] B) {
        // DP[c][t] = max amount of friends to invite for c coffees and t teas
        int[][] DP = new int[k + 1][k + 1];
        // Loop friendlist and for each friend update the DP table with max amount
        // of friends to invite per cofee / tea amount combination
        for(int i = 0; i < n; i++){
            // Get amount of coffee / tea the current friend drinks
            int coffeeAmount = A[i];
            int teaAmount = B[i];
            // Loop down for coffee and tea amount from maximum k to min amount needed
            // for the current person. Note that we need to count downwards as we want
            // the recursion values (c - cA / t - teaAmount) to be from the run with
            // the last person and is not already updated by the current one
            for(int c = k; c >= coffeeAmount; c--){
                for(int t = k; t >= teaAmount; t--){
                    DP[c][t] = Math.max(
                            // Case 1: Do not include the current friend
                            DP[c][t],
                            // Case 2: Include the current friend (amount of friends is
                            // max friend amount for coffees and tea without current friend
                            // drinking plus 1 for current friend)
                            DP[c - coffeeAmount][t - teaAmount] + 1
                    );
                }
            }
        }
        return DP[k][k];
    }

    /**
     * Given a list of friends (length n) and the amount of coffee (A) and tea (B) they drink, find the maximum amount of friends to invite
     * to a party with k coffees and k teas available.
     * As a subproblem we use the minimum amount of tea needed for a given amount of friends and coffees available.
     * Runs in O(n^2 * k) time and O(n * k) space.
     *
     * @param n Amount of friends
     * @param k Amount of coffees / teas available
     * @param A Amount of coffee each friend drinks
     * @param B Amount of tea each friend drinks
     * @return Maximum amount of friends to invite
     */
    public static int maxFriends2(int n, int k, int[] A, int[] B) {
        // DP[i][c] = min tea you need for i friends and c coffee
        int[][] DP = new int[n + 1][k + 1];
        // Initialize all with infinity (as we search for min values)
        for(int i = 0; i <= n; i++){
            for(int j = 0; j <= k; j++){
                DP[i][j] = Integer.MAX_VALUE;
            }
        }
        // Base case: 0 friends (and 0 coffee) requires 0 tea
        DP[0][0] = 0;
        // Loop friendlist and for each friend update the DP table
        for(int i = 0; i < n; i++){
            // Get amount of coffee / tea the current friend drinks
            int coffeeAmount = A[i];
            int teaAmount = B[i];

            for(int j = n - 1; j >= 0; j--){
                for(int c = k; c >= coffeeAmount; c--){
                    // Check the number of tea for the amount of coffee without
                    // the current friend invited is not impossible
                    if (DP[j][c - coffeeAmount] != Integer.MAX_VALUE) {
                        // The new amount of tea for the current friend involved
                        // is the minimum of the amount before and the value
                        // of the tea amount without the current friend
                        // plus the amount of tea this friend drinks
                        DP[j + 1][c] = Math.min(
                                DP[j + 1][c],
                                DP[j][c - coffeeAmount] + teaAmount
                        );
                    }
                }
            }
        }
        // Find the position with the biggest friend amount (search from n downwards)
        // which requires <= k tea / coffee
        for (int i = n; i >= 0; i--) {
            for(int c = 0; c <= k; c++){
                if(DP[i][c] <= k){
                    return i;
                }
            }
        }
        // Nothing possible, we cannot invite anyone :(
        return 0;
    }
}
