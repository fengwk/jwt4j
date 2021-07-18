package fun.fengwk.jwt4j.support;

import fun.fengwk.jwt4j.JWT;
import fun.fengwk.jwt4j.SignException;
import fun.fengwk.jwt4j.ValidateException;
import fun.fengwk.jwt4j.Validator;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * @author fengwk
 */
public class RS256Validator implements Validator, RS256 {

    @Override
    public String alg() {
        return JWT.ALG_RS256;
    }

    @Override
    public boolean validate(byte[] headerAndPayload, byte[] sign, Key key) {
        if (!(key instanceof PublicKey)) {
            throw new SignException("RS256Validator's key must be public key");
        }
        PublicKey publicKey = (PublicKey) key;

        try {
            Signature signature = Signature.getInstance(SHA256_WITH_RSA);
            signature.initVerify(publicKey);
            signature.update(headerAndPayload);
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new ValidateException(e);
        }
    }

}
