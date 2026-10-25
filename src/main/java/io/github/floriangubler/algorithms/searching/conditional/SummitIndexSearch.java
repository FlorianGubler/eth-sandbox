package io.github.floriangubler.algorithms.searching.conditional;

public class SummitIndexSearch {
    public static void main(String[] args){
        System.out.println(findPeak(new int[]{-451992, 81649, 261189, 413270, 904757}));
    }

    public static int findPeak(int[] o) {
        int n = o.length;
        // Loop until second last element
        for(int i = 0; i <= n - 2; i++){
            // If current element is bigger than next, we are at summit
            if(o[i] > o[i + 1]){
                return i;
            }
        }
        // If not returned before, last element is biggest
        return n - 1;
    }
}
