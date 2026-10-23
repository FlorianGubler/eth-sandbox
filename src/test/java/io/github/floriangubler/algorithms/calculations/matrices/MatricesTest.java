package io.github.floriangubler.algorithms.calculations.matrices;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
        double[][] rightHandSide = {
                {8},
                {-11},
                {-3}
        };

        GaussElimination.GaussResult result =
                GaussElimination.gaussElimination(matrix, rightHandSide);

        assertTrue(result.success);
        assertMatrixEquals(new double[][]{
                {2, 1, -1},
                {0, 0.5, 0.5},
                {0, 0, -1}
        }, result.U);
        assertMatrixEquals(new double[][]{
                {8},
                {1},
                {1}
        }, result.C);
    }

    @Test
    void exchangesRowsWhenPivotIsZero() throws Exception {
        double[][] matrix = {
                {0, 2},
                {1, 3}
        };
        double[][] rightHandSide = {
                {4},
                {5}
        };

        GaussElimination.GaussResult result =
                GaussElimination.gaussElimination(matrix, rightHandSide);

        assertTrue(result.success);
        assertMatrixEquals(new double[][]{
                {1, 3},
                {0, 2}
        }, result.U);
        assertMatrixEquals(new double[][]{
                {5},
                {4}
        }, result.C);
    }

    @Test
    void reportsFailureForSingularMatrix() throws Exception {
        double[][] matrix = {
                {1, 2},
                {2, 4}
        };
        double[][] rightHandSide = {
                {3},
                {6}
        };

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
        double[][] rightHandSides = {
                {8, 16},
                {-11, -22},
                {-3, -6}
        };

        GaussElimination.GaussResult result =
                GaussElimination.gaussElimination(matrix, rightHandSides);

        assertTrue(result.success);

        assertMatrixEquals(new double[][]{
                {2, 1, -1},
                {0, 0.5, 0.5},
                {0, 0, -1}
        }, result.U);
        assertMatrixEquals(new double[][]{
                {8, 16},
                {1, 2},
                {1, 2}
        }, result.C);
    }

    @Test
    void testBackSubstitution(){
        double[][] matrix = {
                {2, 1, -2},
                {0, -1, 2},
                {0, 0, 2}
        };
        double[] vector1 = {8, -11, -3};

        double[] result = GaussElimination.backSubstitution(matrix, vector1);

        assertArrayEquals(new double[]{-1.5, 8, -3 / 2.0}, result);
    }

    @Test
    void testInverseGaussMatrix(){
        double[][] matrix = {
                {1, 0, 0},
                {0, 0, 1},
                {0, 1, 0}
        };

        double[][] result = InverseMatrix.inverseMatrixGauss(matrix);

        assertArrayEquals(matrix, result);
    }

    @Test
    void testInverseMatrixGauss2(){
        double[][] matrix = {
                {2, 3},
                {1, 4}
        };

        double[][] result = InverseMatrix.inverseMatrixGauss(matrix);

        assertMatrixEquals(new double[][]{
                {4.0/5, -3.0/5},
                {-1.0/5, 2.0/5}
        }, result);
    }

    @Test
    void testInverseMatrixFail(){
        double[][] matrix = {
                {0, 1},
                {0, 1}
        };

        assertThrows(IllegalArgumentException.class, () -> InverseMatrix.inverseMatrixGauss(matrix));
        assertThrows(IllegalArgumentException.class, () -> InverseMatrix.inverseMatrixGaussJordan(matrix));
    }

    @Test
    void testGaussJordanElimination(){
        double[][] matrix = {
                {2, 4, 2, 2, -2},
                {6, 12, 6, 7, 1},
                {4, 8, 2, 2, 6}
        };
        double[][] rightHandSide = {
                {1},
                {2},
                {3}
        };

        GaussJordanElimination.GaussJordanResult result =
                GaussJordanElimination.gaussJordanElimination(matrix, rightHandSide);

        assertMatrixEquals(new double[][]{
                {1, 2, 0, 0, 4},
                {0, 0, 1, 0, -12},
                {0, 0, 0, 1, 7}
        }, result.R);
        System.out.println(Arrays.toString(result.C[2]));
        assertMatrixEquals(new double[][]{
                {1},
                {0.5},
                {-1}
        }, result.C);
    }

    @Test
    void testInverseGaussJordanMatrix(){
        double[][] matrix = {
                {1, 0, 0},
                {0, 0, 1},
                {0, 1, 0}
        };

        double[][] result = InverseMatrix.inverseMatrixGaussJordan(matrix);

        assertArrayEquals(matrix, result);
    }

    @Test
    void testInverseMatrixGaussJordan2(){
        double[][] matrix = {
                {2, 3},
                {1, 4}
        };

        double[][] result = InverseMatrix.inverseMatrixGaussJordan(matrix);

        assertMatrixEquals(new double[][]{
                {4.0/5, -3.0/5},
                {-1.0/5, 2.0/5}
        }, result);
    }

    @Test
    void testDirectSolution(){
        double[][] matrix = {
                {2, 4, 2, 2, -2},
                {6, 12, 6, 7, 1},
                {4, 8, 2, 2, 6}
        };
        double[][] rightHandSide = {
                {1},
                {2},
                {3}
        };

        GaussJordanElimination.GaussJordanResult r = GaussJordanElimination.gaussJordanElimination(matrix, rightHandSide);
        double[] result = GaussJordanElimination.directSolution(
                r.R,
                r.downwardStepIndices,
                new double[]{r.C[0][0], r.C[1][0], r.C[2][0]}
        );

        assertArrayEquals(new double[]{1, 0, 0.5, -1, 0}, result);    }

    private static void assertMatrixEquals(double[][] expected, double[][] actual) {
        assertEquals(expected.length, actual.length);
        for (int row = 0; row < expected.length; row++) {
            assertArrayEquals(expected[row], actual[row], DELTA);
        }
    }
}
