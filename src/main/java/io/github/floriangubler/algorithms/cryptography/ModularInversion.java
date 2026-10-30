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

        EuclideanAlgorithm.ExtendedResult result =
                EuclideanAlgorithm.extendedEuclideanAlgorithm(a, m);
        if (result.gcd != 1) {
            throw new IllegalArgumentException("Inverse does not exist");
        }
        return Math.floorMod(result.x, m);
    }
}
