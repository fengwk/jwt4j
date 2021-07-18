package fun.fengwk.jwt4j.support;

import fun.fengwk.jwt4j.JWT;
import fun.fengwk.jwt4j.SignException;
import fun.fengwk.jwt4j.Signer;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * @author fengwk
 */
public class RS256Signer implements Signer, RS256 {

    @Override
    public String alg() {
        return JWT.ALG_RS256;
    }

    @Override
    public byte[] sign(byte[] encodedHeaderAndPayload, Key key) {
        if (!(key instanceof PrivateKey)) {
            throw new SignException("RS256Signer's key must be private key");
        }
        PrivateKey privateKey = (PrivateKey) key;

        try {
            Signature signature = Signature.getInstance(SHA256_WITH_RSA);
            signature.initSign(privateKey);
            signature.update(encodedHeaderAndPayload);
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new SignException(e);
        }
    }

}
