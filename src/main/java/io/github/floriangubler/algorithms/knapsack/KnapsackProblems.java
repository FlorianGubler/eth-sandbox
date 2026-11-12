package io.github.floriangubler.algorithms.knapsack;

public class KnapsackProblems {

    /**
     * Given a list of friends (length n) and the amount of coffee (A) and tea (B) they drink, find the maximum amount of friends to invite
     * to a party with k coffees and k teas available.
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
}
