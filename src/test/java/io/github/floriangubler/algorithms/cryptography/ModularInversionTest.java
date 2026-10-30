package io.github.floriangubler.algorithms.cryptography;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModularInversionTest {

    @Test
    void calculatesTheModularInverse() {
        assertEquals(4, ModularInversion.modularInverse(3, 11));
        assertEquals(12, ModularInversion.modularInverse(10, 17));
    }

    @Test
    void returnedInverseSatisfiesTheCongruenceForNegativeInputs() {
        long inverse = ModularInversion.modularInverse(-3, 11);

        assertEquals(1, Math.floorMod(-3 * inverse, 11));
    }

    @Test
    void rejectsNumbersThatAreNotCoprimeToTheModulus() {
        assertThrows(IllegalArgumentException.class,
                () -> ModularInversion.modularInverse(6, 15));
    }
}
