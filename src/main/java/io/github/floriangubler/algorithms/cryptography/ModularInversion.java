package io.github.floriangubler.algorithms.cryptography;

import io.github.floriangubler.algorithms.calculations.general.EuclideanAlgorithm;

public class ModularInversion {

    /**
     * Computes the modular inverse of a modulo m using the extended Euclidean algorithm.
     * Modular inverse exists if and only if a and m fulfill gcd(a, m) = 1
     *
     * @param a the number to find the inverse of
     * @param m the modulus
     * @return the least non-negative modular inverse of a modulo m
     * @throws IllegalArgumentException if m is not positive or the modular inverse
     *         does not exist
     */
    public static long modularInverse(long a, long m) {
        if (m <= 0) {
            throw new IllegalArgumentException("Modulus must be positive");
        }
        // Solve the Bézout identity a * x + m * y = gcd(a, m) for x and y.
        // If gcd(a, m) = 1, then x is the modular inverse of a modulo m.
        EuclideanAlgorithm.ExtendedResult result =
                EuclideanAlgorithm.extendedEuclideanAlgorithm(a, m);
        if (result.gcd != 1) {
            throw new IllegalArgumentException("Inverse does not exist");
        }
        // The modular inverse is the coefficient of a in the Bézout identity,
        // reduced modulo m (as it is defined as the least non-negative solution).
        return Math.floorMod(result.x, m);
    }
}
