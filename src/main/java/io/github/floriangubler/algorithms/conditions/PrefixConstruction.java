package io.github.floriangubler.algorithms.conditions;

public class PrefixConstruction {

    public static void main(String[] args) {
        System.out.println(isPrefixConstruction("aaa", "a", 4)); // true
        System.out.println(isPrefixConstruction("aaa", "a", 2)); // false
        System.out.println(isPrefixConstruction("aaaaaaa", "aaa", 3)); // true
        System.out.println(isPrefixConstruction("abcababc", "abc", 4)); // true
        System.out.println(isPrefixConstruction("abcbcabc", "abc", 4)); // false
        System.out.println(isPrefixConstruction("abab", "abac", 2)); // true
    }

    /**
     * Given are two strings s and t and an integer n with n ≥ 0. This method returns whether s
     * is a concatenation of at most n prefixes of t.
     * Note: We try the biggest prefixes first, so even if smaller options would result in more than n true is returned
     *
     * @param s the string to check
     * @param t the prefix string
     * @param n the max number of times to concatenate prefixes of t
     * @return true if the number of prefixes of t concatenated together to form s is at most n, false otherwise
     */
    public static boolean isPrefixConstruction(String s, String t, int n) {
        return isPrefixConstruction(s, t, n, 0);
    }

    /**
     * Given are two strings s and t and an integer n with n ≥ 0. This method returns whether s
     * is a concatenation of at most n prefixes of t.
     * Note: We try the biggest prefixes first, so even if smaller options would result in more than n true is returned
     *
     * @param s the string to check
     * @param t the prefix string
     * @param n the max number of times to concatenate prefixes of t
     * @param prefixCutLength the length of the prefix to cut off from t (starts at 0)
     * @return true if the number of prefixes of t concatenated together to form s is at most n, false otherwise
     */
    public static boolean isPrefixConstruction(String s, String t, int n, int prefixCutLength) {
        String cutPrefix = t.substring(0, t.length() - prefixCutLength);
        // If we got an empty string, we can always construct it with 0 prefixes
        if(s.isEmpty()) {
            return true;
        } else if(t.length() == prefixCutLength) {
            // If t is empty, we cannot construct any non-empty string
            return false;
        }
        // If n is 0 check that s does not contain any more prefixes of t
        if(n == 0){
            // Check that s does not contain the smallest prefix of t (which is just the first character)
            return !s.contains(t.substring(0, 1));
        }
        // Loop through the prefixes
        if(s.startsWith(cutPrefix)){
            // If the string starts with the current prefix, we can cut it off and check the rest of the string with n-1
            if(isPrefixConstruction(s.substring(cutPrefix.length()), t, n - 1, 0)){
                // If we can construct the rest of the string with n-1 prefixes, we return true
                return true;
                // If we could not construct the rest of the string with n-1 prefixes, we try the next smaller prefix
            } else {
                return isPrefixConstruction(s, t, n, ++prefixCutLength);
            }
            // If the string does not start with the current prefix, we try the next smaller prefix
        } else {
            return isPrefixConstruction(s, t, n, ++prefixCutLength);
        }
    }
}
