package io.github.floriangubler.algorithms.cryptography;

public class EuclideanAlgorithm {

    /**
     * Calculates the greatest common divisor using Euclid's algorithm.
     *
     * @param a first number
     * @param b second number
     * @return the non-negative greatest common divisor (gcd) of a and b
     */
    public static long euclideanAlgorithm(long a, long b) {
        // Get absolute value as gcd is defined to be non-negative.
        long first = Math.abs(a);
        long second = Math.abs(b);
        // We use the fact that gcd(a, b) = gcd(b, a mod b) and that a mod b is always smaller than b.
        // Therefore, the second operand gets smaller and smaller until it is zero, at which point
        // the first operand is the gcd.
        while (second != 0) {
            long remainder = first % second;
            first = second;
            second = remainder;
        }
        return first;
    }

    /**
     * Calculates the gcd and Bézout coefficients using the extended
     * Euclidean algorithm.
     *
     * <p>The returned values satisfy
     * {@code a * x + b * y = gcd(a, b)}. The gcd is always non-negative,
     * including when either input is negative.</p>
     *
     * @param a first integer
     * @param b second integer
     * @return the gcd and coefficients of the Bézout identity
     */
    public static ExtendedResult extendedEuclideanAlgorithm(long a, long b) {
        // Run the remainder calculation on non-negative values so the gcd remains non-negative.
        long oldRemainder = Math.abs(a);
        long remainder = Math.abs(b);
        // The coefficients of the Bézout identity are initialized to the identity matrix.
        long oldX = 1;
        long x = 0;
        long oldY = 0;
        long y = 1;

        // We make the equation abs(a) * oldX + abs(b) * oldY = oldRemainder smaller and smaller until
        // the remainder is zero. At that point, the old remainder is the gcd and the old coefficients are
        // the Bézout coefficients.
        // To make the equation smaller we use Euclidean division. It states that for any two integers u and v,
        // there exist unique integers q and r such that u = v * q + r, where 0 <= r < |v|.
        // Therefore, the remainder r is smaller than v, which means that the next equation is smaller than
        // the current one. The gcd does not change because every divisor of u and v also divides
        // u - v * q = r, and every divisor of v and r also divides v * q + r = u.
        while (remainder != 0) {
            // Calculate how many times remainder fits into oldRemainder. This is valid because Euclidean
            // division lets us write oldRemainder as quotient * remainder plus a remainder smaller than
            // the current remainder.
            long quotient = oldRemainder / remainder;

            // Calculate the new, smaller remainder:
            // oldRemainder = quotient * remainder + nextRemainder.
            // Subtracting quotient * remainder is allowed because it does not change the gcd, and it gives
            // 0 <= nextRemainder < remainder. This is what makes the equation smaller in every iteration.
            long nextRemainder = oldRemainder - quotient * remainder;
            oldRemainder = remainder;
            remainder = nextRemainder;

            // Apply the same subtraction to the Bézout coefficients. Since remainder is represented by
            // abs(a) * x + abs(b) * y, oldRemainder - quotient * remainder is represented by
            // abs(a) * (oldX - quotient * x) + abs(b) * (oldY - quotient * y).
            // This keeps the coefficients in sync with the smaller remainder.
            long nextX = oldX - quotient * x;
            oldX = x;
            x = nextX;

            long nextY = oldY - quotient * y;
            oldY = y;
            y = nextY;
        }
        // The calculation above used absolute values. Restore the signs so that the
        // coefficients satisfy the Bézout identity for the original inputs.
        if (a < 0) {
            oldX = -oldX;
        }
        if (b < 0) {
            oldY = -oldY;
        }
        return new ExtendedResult(oldRemainder, oldX, oldY);
    }

    /**
     * Immutable result of the extended Euclidean algorithm.
     */
    public static final class ExtendedResult {
        public final long gcd;
        public final long x;
        public final long y;

        private ExtendedResult(long gcd, long x, long y) {
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }
    }
}