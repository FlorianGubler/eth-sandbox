package io.github.floriangubler.algorithms.cryptography;

import java.util.Random;

/**
 * A simple implementation of the Diffie-Hellman key agreement protocol. The protocol uses advantage of the fact
 * that the rest of a number can be computed easily, but inverse of that is very hard to compute.
 * Usage: This protocol allows two participants to establish a shared secret over an insecure channel.
 * The shared secret can then be used for secure communication.
 *
 * @author Florian Gubler
 */
public class DiffieHellmanKeyAgreementProtocol {

    /**
     * The global prime number used in the Diffie-Hellman key agreement protocol.
     */
    private static final int PRIME_NUMBER = 23;

    /**
     * The global generator used in the Diffie-Hellman key agreement protocol.
     */
    private static final int GENERATOR = 5;

    /**
     * Represents a participant in the Diffie-Hellman key agreement protocol.
     * Each participant has a random private key and can compute its public key with Diffie-Hellman parameters.
     * The participant can also compute the shared secret using the other participant's public key and its own private key.
     */
    public static class Participant {

        /**
         * The private key of the participant, randomly generated.
         * Note: The private key should be in the range [2, PRIME_NUMBER - 1]
         * to ensure security because 0 and 1 do not provide enough entropy for the shared secret
         * and keys bigger than the prime number are not valid as they will be reduced modulo the prime number.
         */
        private final int privateKey = new Random().nextInt(PRIME_NUMBER - 2) + 2;

        /**
         * Computes the public key based on the private key, generator, and prime number.
         *
         * @return The computed public key.
         */
        public int getPublicKey() {
            return modularExponentiation(GENERATOR, privateKey);
        }

        /**
         * Computes the shared secret using the other participant's public key and the participant's private key.
         *
         * @param otherPublicKey The public key of the other participant.
         * @return The computed shared secret.
         */
        public int computeSharedSecret(int otherPublicKey) {
            return modularExponentiation(otherPublicKey, privateKey);
        }

        private int modularExponentiation(int base, int exponent) {
            long result = 1;
            long reducedBase = Math.floorMod(base, PRIME_NUMBER);

            while (exponent > 0) {
                if ((exponent & 1) == 1) {
                    result = result * reducedBase % PRIME_NUMBER;
                }
                reducedBase = reducedBase * reducedBase % PRIME_NUMBER;
                exponent >>= 1;
            }

            return (int) result;
        }
    }
}
