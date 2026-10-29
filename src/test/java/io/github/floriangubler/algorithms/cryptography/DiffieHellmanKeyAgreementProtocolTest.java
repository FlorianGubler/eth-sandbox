package io.github.floriangubler.algorithms.cryptography;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiffieHellmanKeyAgreementProtocolTest {

    @Test
    void participantPublishesKeyWithinPrimeNumberRange() {
        int publicKey = new DiffieHellmanKeyAgreementProtocol.Participant().getPublicKey();

        assertTrue(publicKey >= 0);
        assertTrue(publicKey < 23);
    }

    @Test
    void participantsComputeTheSameSharedSecret() {
        DiffieHellmanKeyAgreementProtocol.Participant firstParticipant =
                new DiffieHellmanKeyAgreementProtocol.Participant();
        DiffieHellmanKeyAgreementProtocol.Participant secondParticipant =
                new DiffieHellmanKeyAgreementProtocol.Participant();

        int firstSharedSecret = firstParticipant.computeSharedSecret(secondParticipant.getPublicKey());
        int secondSharedSecret = secondParticipant.computeSharedSecret(firstParticipant.getPublicKey());

        assertEquals(firstSharedSecret, secondSharedSecret);
    }
}
