package io.github.floriangubler.algorithms.calculations.general;

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

        // We make the equation a * x + b * y = gcd(a, b) smaller and smaller until the remainder is zero. At
        // that point, the old remainder is the gcd and the old coefficients are the Bézout coefficients.
        // To make the equation smaller we use Euclidean division, which states that for any two integers a and b,
        // there exist unique integers q and r such that a = b * q + r, where 0 <= r < |b|.
        while (remainder != 0) {
            // Calculate the quotient of the Euclidean division of oldRemainder by remainder.
            long quotient = oldRemainder / remainder;

            // Euclidean division: oldRemainder = quotient * remainder + nextRemainder.
            long nextRemainder = oldRemainder - quotient * remainder;
            oldRemainder = remainder;
            remainder = nextRemainder;

            // Apply the same linear combination to the Bézout coefficients.
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