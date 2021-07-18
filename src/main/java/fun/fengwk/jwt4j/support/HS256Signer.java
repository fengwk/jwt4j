package fun.fengwk.jwt4j.support;

import fun.fengwk.jwt4j.JWT;
import fun.fengwk.jwt4j.SignException;
import fun.fengwk.jwt4j.Signer;

import javax.crypto.Mac;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author fengwk
 */
public class HS256Signer implements Signer, HS256 {

    @Override
    public String alg() {
        return JWT.ALG_HS256;
    }

    @Override
    public byte[] sign(byte[] encodedHeaderAndPayload, Key key) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(key);
            byte[] digest = mac.doFinal(encodedHeaderAndPayload);
            return digest;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new SignException(e);
        }
    }

}
