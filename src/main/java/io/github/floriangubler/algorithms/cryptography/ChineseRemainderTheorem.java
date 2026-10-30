package io.github.floriangubler.algorithms.cryptography;

public class ChineseRemainderTheorem {

    /**
     * Solves a system of simultaneous congruences using the Chinese Remainder Theorem in the form
     * x = r1 (mod m1), x = r2 (mod m2), ..., x = rn (mod mn). Note that all the moduli (mj) must
     * be > 0 and pairwise coprime for a solution to exist (i.e., gcd(mi, mj) = 1 for all i != j).
     *
     * @param residues the residues of the congruences
     * @param moduli   the moduli of the congruences
     * @return a solution to the system of congruences
     */
    public static long chineseRemainderTheorem(long[] residues, long[] moduli) {
        if (residues.length != moduli.length) {
            throw new IllegalArgumentException("The number of residues must be equal to the number of moduli.");
        }
        // Calculate the product of all moduli M
        long M = 1;
        for (long modulus : moduli) {
            if (modulus <= 0) {
                throw new IllegalArgumentException("All moduli must be greater than 0.");
            }
            M *= modulus;
        }
        // Calculate the Mi values, which are the product of all moduli divided by the current modulus.
        long[] Mi = new long[moduli.length];
        for (int i = 0; i < moduli.length; i++) {
            Mi[i] = M / moduli[i];
        }
        // Choose Ni such that Mi * Ni ≡ 1 (mod mi) for each i. This can be done using the extended Euclidean algorithm.
        long[] Ni = new long[moduli.length];
        for (int i = 0; i < moduli.length; i++) {
            EuclideanAlgorithm.ExtendedResult result = EuclideanAlgorithm.extendedEuclideanAlgorithm(Mi[i], moduli[i]);
            if (result.gcd != 1) {
                throw new IllegalArgumentException("Moduli must be pairwise coprime. gcd(" + Mi[i] + ", " + moduli[i] + ") = " + result.gcd);
            }
            Ni[i] = result.x; // x is the multiplicative inverse of Mi[i]
        }
        // The solution then is Σ (ri * Mi * Ni) because each term will be
        // congruent to ri modulo mi and 0 modulo all other moduli.
        long L = 0;
        for (int i = 0; i < residues.length; i++) {
            L += residues[i] * Mi[i] * Ni[i];
        }
        return Math.floorMod(L, M); // Return the smallest non-negative solution
    }
}
