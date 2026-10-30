package io.github.floriangubler.algorithms.cryptography;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChineseRemainderTheoremTest {

    @Test
    void solvesASystemOfThreeCongruences() {
        long result = ChineseRemainderTheorem.chineseRemainderTheorem(
                new long[]{2, 3, 2},
                new long[]{3, 5, 7});

        assertEquals(23, result);
    }

    @Test
    void returnsTheSmallestNonNegativeSolution() {
        long result = ChineseRemainderTheorem.chineseRemainderTheorem(
                new long[]{1, 2},
                new long[]{4, 5});

        assertEquals(17, result);
    }

    @Test
    void handlesResiduesOutsideTheCanonicalRange() {
        long result = ChineseRemainderTheorem.chineseRemainderTheorem(
                new long[]{-1, 8},
                new long[]{3, 5});

        assertEquals(8, result);
    }
}
