package io.github.floriangubler.algorithms.cryptography;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EuclideanAlgorithmTest {

    @Test
    void calculatesTheGreatestCommonDivisor() {
        assertEquals(6, EuclideanAlgorithm.euclideanAlgorithm(48, 18));
        assertEquals(6, EuclideanAlgorithm.euclideanAlgorithm(18, 48));
    }

    @Test
    void handlesZeroAndNegativeInputs() {
        assertEquals(7, EuclideanAlgorithm.euclideanAlgorithm(0, -7));
        assertEquals(35, EuclideanAlgorithm.euclideanAlgorithm(-35, 0));
        assertEquals(0, EuclideanAlgorithm.euclideanAlgorithm(0, 0));
    }

    @Test
    void calculatesBezoutCoefficients() {
        EuclideanAlgorithm.ExtendedResult result =
                EuclideanAlgorithm.extendedEuclideanAlgorithm(240, 46);

        assertEquals(2, result.gcd);
        assertEquals(2, 240L * result.x + 46L * result.y);
    }

    @Test
    void extendedAlgorithmNormalizesTheGcdForNegativeInputs() {
        EuclideanAlgorithm.ExtendedResult result =
                EuclideanAlgorithm.extendedEuclideanAlgorithm(-240, 46);

        assertEquals(2, result.gcd);
        assertEquals(2, -240L * result.x + 46L * result.y);
    }

    @Test
    void extendedAlgorithmHandlesZeroOperands() {
        EuclideanAlgorithm.ExtendedResult result =
                EuclideanAlgorithm.extendedEuclideanAlgorithm(0, -7);

        assertEquals(7, result.gcd);
        assertEquals(7, 0L * result.x - 7L * result.y);
    }
}
