package fun.fengwk.jwt4j.support;

import fun.fengwk.jwt4j.JWT;
import fun.fengwk.jwt4j.SignException;
import fun.fengwk.jwt4j.ValidateException;
import fun.fengwk.jwt4j.Validator;

import java.security.Key;
import java.util.Arrays;

/**
 * @author fengwk
 */
public class HS256Validator implements Validator, HS256 {

    private final HS256Signer hs256Signer = new HS256Signer();

    @Override
    public String alg() {
        return JWT.ALG_HS256;
    }

    @Override
    public boolean validate(byte[] headerAndPayload, byte[] signature, Key key) {
        try {
            byte[] sign = hs256Signer.sign(headerAndPayload, key);
            return Arrays.equals(sign, signature);
        } catch (SignException e) {
            throw new ValidateException(e);
        }
    }

}
