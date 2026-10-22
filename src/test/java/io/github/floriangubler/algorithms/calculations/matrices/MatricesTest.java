package io.github.floriangubler.algorithms.calculations.matrices;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class MatricesTest {

    private static final double DELTA = 1e-10;

    @Test
    void eliminatesMatrixAndRightHandSide() throws Exception {
        double[][] matrix = {
                {2, 1, -1},
                {-3, -1, 2},
                {-2, 1, 2}
        };
        double[] rightHandSide = {8, -11, -3};

        GaussElimination.GaussResult result =
                GaussElimination.gaussElimination(matrix, rightHandSide);

        assertTrue(result.success);
        assertMatrixEquals(new double[][]{
                {2, 1, -1},
                {0, 0.5, 0.5},
                {0, 0, -1}
        }, result.U);
        assertArrayEquals(new double[]{8, 1, 1}, result.C[0], DELTA);
    }

    @Test
    void exchangesRowsWhenPivotIsZero() throws Exception {
        double[][] matrix = {
                {0, 2},
                {1, 3}
        };
        double[] rightHandSide = {4, 5};

        GaussElimination.GaussResult result =
                GaussElimination.gaussElimination(matrix, rightHandSide);

        assertTrue(result.success);
        assertMatrixEquals(new double[][]{
                {1, 3},
                {0, 2}
        }, result.U);
        assertArrayEquals(new double[]{5, 4}, result.C[0], DELTA);
    }

    @Test
    void reportsFailureForSingularMatrix() throws Exception {
        double[][] matrix = {
                {1, 2},
                {2, 4}
        };
        double[] rightHandSide = {3, 6};

        GaussElimination.GaussResult result =
                GaussElimination.gaussElimination(matrix, rightHandSide);

        assertFalse(result.success);
    }

    @Test
    void testMultipleRightSideVectors() throws Exception {
        double[][] matrix = {
                {2, 1, -1},
                {-3, -1, 2},
                {-2, 1, 2}
        };
        double[] vector1 = {8, -11, -3};
        double[] vector2 = {16, -22, -6};

        GaussElimination.GaussResult result =
                GaussElimination.gaussElimination(matrix, vector1, vector2);

        assertTrue(result.success);

        assertMatrixEquals(new double[][]{
                {2, 1, -1},
                {0, 0.5, 0.5},
                {0, 0, -1}
        }, result.U);
        assertArrayEquals(new double[]{8, 1, 1}, result.C[0], DELTA);
        assertArrayEquals(new double[]{16, 2, 2}, result.C[1], DELTA);
    }

    @Test
    void testBackSubstitution(){
        double[][] matrix = {
                {2, 1, -2},
                {0, -1, 2},
                {0, 0, 2}
        };
        double[] vector1 = {8, -11, -3};

        double[] result = BackSubstitution.backSubstitution(matrix, vector1);

        assertArrayEquals(new double[]{-1.5, 8, -3 / 2.0}, result);
    }

    @Test
    void testInverseMatrix(){
        double[][] matrix = {
                {1, 0, 0},
                {0, 0, 1},
                {0, 1, 0}
        };

        double[][] result = InverseMatrix.inverseMatrix(matrix);

        assertArrayEquals(matrix, result);
    }

    private static void assertMatrixEquals(double[][] expected, double[][] actual) {
        assertEquals(expected.length, actual.length);
        for (int row = 0; row < expected.length; row++) {
            assertArrayEquals(expected[row], actual[row], DELTA);
        }
    }
}
