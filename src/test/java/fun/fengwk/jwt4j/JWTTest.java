package fun.fengwk.jwt4j;

import fun.fengwk.jwt4j.support.HS256;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author fengwk
 */
public class JWTTest {

    @Test
    public void genHS256Key() throws NoSuchAlgorithmException {
        KeyGenerator gen = KeyGenerator.getInstance(HS256.HMAC_SHA256);
        SecretKey secretKey = gen.generateKey();
        System.out.println(secretKey.getFormat());
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        /*
         * RAW
         * hfMEi76AxX59Ky7nzQ4UxoRqnJskF9K0ahTIu7l3wJE=
         */
    }

    @Test
    public void genRS256Key() throws NoSuchAlgorithmException {
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(2048);
        KeyPair keyPair = gen.generateKeyPair();

        System.out.println("privateKey:");
        System.out.println(keyPair.getPrivate().getFormat());
        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        System.out.println("publicKey:");
        System.out.println(keyPair.getPublic().getFormat());
        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));

        /*
         * privateKey:
         * PKCS#8
         * MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHexZRzwBW+b6iVSgA1SSOMXTWYjnsVyK+cDnV56wpomA9+I2jJX6TmxV6tv+BVwzqT6B7LLDdZBDAPryHJuF9RurG2pzM10HN7+h69IkxMiQjFu+JaXUaLOLFFWSsBaFpwMH/TXkF6lLA7YwpKQNkgbhRJb9c8n2VjIfnawV193ZZuS6IoZQ4VhjMFKp9XRT5/q/8PFJZAyqquDRLywAEMXXKe85w1m1sbI2TQnXkgydltWOvsA3y4hswyHcj5YcRr9PdmXw1mYHMGg0tjOLBf2pUP2Skhfh1/bGR83rGS/KZSlOPVu6uiBbGZPg0/Es+fZxkTVr//TO2/Pk1+HrFAgMBAAECggEBAK2dXEShZAtO2/MPKHzpBSLYunaoEP+4qt2ScVPRic7Gqvrkq+lVhb/UFzKxuNkKOYxo9ySdNeXJ5KZdM9pxJYnSQh4GQLgrlPM2nWD+GbV+jWMY4Cv5Y9j/PfMH9pGdaOnQwUeBVxal5ZZSaU2di31bRV8i+lVcG58gK1xnkd4jlBOBdgKd9zgyUljFzWQA5Q6R6aRsVWyLXfLjhRnisPXNUvO1WUE4cTzxt7wkwjh2GIDIi7X6SxpKZX5z+h0NjeX9dgpc8xCQA2AoaC2w7dZp1vL40J1oCP5bS5GqVgI7NGScGFc8pVYdWl8e5yKpaQzwDaJaK2YmXgZVfbxuycECgYEA6FWwF4ByoBUip8jPsoWkBSdv0C8ILFg+0XUifkpc5kC2n1tCg5nXhhFJecx6wJ1CxJFj3H/DalyH5ddaOYWCPcF8pl3AqS50l6mbKt1azx0KCWI83J749hm/WCOoQ86grhLKwqp11hFyro/9XjTMP++r8xT8RDhnyZkJNTR7jbUCgYEA28y1JCMUv3/qNKxuj0TQj8A3E3DYtRl2NkEgUZqLZBuSdbyqZOHN96DaDUhh5aoGhBLdflfbATLcdKfM+7AChHb52bFdZw75xlKudP2DqPrVpL+wkgnLL7T3+IuPvvAozHM3CZ++Z5ROYc5IXy91f+wPpQ2hA+Rbt+31QWIA4tECgYAEWKYEqoh6airNzLnl8w4Qcp5q3JZgjf4O2QweJLH8NLW14XUrFROtFGG1f5fERqzbFIUzuMglEzoPmKm9bbvz/8yfKB3UjGPt+mRGtwFd4oMvjcDpt6ecovqrYW9iZVkoKH3Wim3N5WGp1lO9EWvIvmkPERNvC0cVxGEht6ugCQKBgQCqsxuGuhv18VChobynVYkn6t6bIYubce/FiOShm4VftK4U8/XdLSR5iRlvMWSeKWKwFbqg0XhLQQXHLz5ZxULfP0shDsb8L7w5kqLcRrqGdG+RaDGn6SU8oq3l//UeE4OL7Hq05bPI23vYJ407Jy94rosj4ybGUAYPHSXYplUL0QKBgD/GYm927mwQrpKqDJoCD4irUJFdt44UMLRGfgiuhbjtMkxMeUPycRTaMuVn/OZ3juol2JuzzNNYN0j0SaT0/NpA0IdvKy4NyX09oRwxP1igKoDjY+OiA5yvjutQXTIY72FWje41hk9ZC5yWi5HJyW9RHWW7X1fLZBg5zJ12lRi8
         * publicKey:
         * X.509
         * MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx3sWUc8AVvm+olUoANUkjjF01mI57FcivnA51eesKaJgPfiNoyV+k5sVerb/gVcM6k+geyyw3WQQwD68hybhfUbqxtqczNdBze/oevSJMTIkIxbviWl1GizixRVkrAWhacDB/015BepSwO2MKSkDZIG4USW/XPJ9lYyH52sFdfd2WbkuiKGUOFYYzBSqfV0U+f6v/DxSWQMqqrg0S8sABDF1ynvOcNZtbGyNk0J15IMnZbVjr7AN8uIbMMh3I+WHEa/T3Zl8NZmBzBoNLYziwX9qVD9kpIX4df2xkfN6xkvymUpTj1burogWxmT4NPxLPn2cZE1a//0ztvz5Nfh6xQIDAQAB
         */
    }

    @Test
    public void testHS256() {
        String base64EncodedSecretKey = "hfMEi76AxX59Ky7nzQ4UxoRqnJskF9K0ahTIu7l3wJE=";
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(base64EncodedSecretKey), HS256.HMAC_SHA256);

        JWT jwt = new JWT.Builder().hs256().claims("user", "fengwk").build(secretKey);
        System.out.println(jwt);

        assert new JWT.Parser(jwt.toString(), secretKey).validate();
    }

    @Test
    public void testRS256() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String base64EncodedPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHexZRzwBW+b6iVSgA1SSOMXTWYjnsVyK+cDnV56wpomA9+I2jJX6TmxV6tv+BVwzqT6B7LLDdZBDAPryHJuF9RurG2pzM10HN7+h69IkxMiQjFu+JaXUaLOLFFWSsBaFpwMH/TXkF6lLA7YwpKQNkgbhRJb9c8n2VjIfnawV193ZZuS6IoZQ4VhjMFKp9XRT5/q/8PFJZAyqquDRLywAEMXXKe85w1m1sbI2TQnXkgydltWOvsA3y4hswyHcj5YcRr9PdmXw1mYHMGg0tjOLBf2pUP2Skhfh1/bGR83rGS/KZSlOPVu6uiBbGZPg0/Es+fZxkTVr//TO2/Pk1+HrFAgMBAAECggEBAK2dXEShZAtO2/MPKHzpBSLYunaoEP+4qt2ScVPRic7Gqvrkq+lVhb/UFzKxuNkKOYxo9ySdNeXJ5KZdM9pxJYnSQh4GQLgrlPM2nWD+GbV+jWMY4Cv5Y9j/PfMH9pGdaOnQwUeBVxal5ZZSaU2di31bRV8i+lVcG58gK1xnkd4jlBOBdgKd9zgyUljFzWQA5Q6R6aRsVWyLXfLjhRnisPXNUvO1WUE4cTzxt7wkwjh2GIDIi7X6SxpKZX5z+h0NjeX9dgpc8xCQA2AoaC2w7dZp1vL40J1oCP5bS5GqVgI7NGScGFc8pVYdWl8e5yKpaQzwDaJaK2YmXgZVfbxuycECgYEA6FWwF4ByoBUip8jPsoWkBSdv0C8ILFg+0XUifkpc5kC2n1tCg5nXhhFJecx6wJ1CxJFj3H/DalyH5ddaOYWCPcF8pl3AqS50l6mbKt1azx0KCWI83J749hm/WCOoQ86grhLKwqp11hFyro/9XjTMP++r8xT8RDhnyZkJNTR7jbUCgYEA28y1JCMUv3/qNKxuj0TQj8A3E3DYtRl2NkEgUZqLZBuSdbyqZOHN96DaDUhh5aoGhBLdflfbATLcdKfM+7AChHb52bFdZw75xlKudP2DqPrVpL+wkgnLL7T3+IuPvvAozHM3CZ++Z5ROYc5IXy91f+wPpQ2hA+Rbt+31QWIA4tECgYAEWKYEqoh6airNzLnl8w4Qcp5q3JZgjf4O2QweJLH8NLW14XUrFROtFGG1f5fERqzbFIUzuMglEzoPmKm9bbvz/8yfKB3UjGPt+mRGtwFd4oMvjcDpt6ecovqrYW9iZVkoKH3Wim3N5WGp1lO9EWvIvmkPERNvC0cVxGEht6ugCQKBgQCqsxuGuhv18VChobynVYkn6t6bIYubce/FiOShm4VftK4U8/XdLSR5iRlvMWSeKWKwFbqg0XhLQQXHLz5ZxULfP0shDsb8L7w5kqLcRrqGdG+RaDGn6SU8oq3l//UeE4OL7Hq05bPI23vYJ407Jy94rosj4ybGUAYPHSXYplUL0QKBgD/GYm927mwQrpKqDJoCD4irUJFdt44UMLRGfgiuhbjtMkxMeUPycRTaMuVn/OZ3juol2JuzzNNYN0j0SaT0/NpA0IdvKy4NyX09oRwxP1igKoDjY+OiA5yvjutQXTIY72FWje41hk9ZC5yWi5HJyW9RHWW7X1fLZBg5zJ12lRi8";
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx3sWUc8AVvm+olUoANUkjjF01mI57FcivnA51eesKaJgPfiNoyV+k5sVerb/gVcM6k+geyyw3WQQwD68hybhfUbqxtqczNdBze/oevSJMTIkIxbviWl1GizixRVkrAWhacDB/015BepSwO2MKSkDZIG4USW/XPJ9lYyH52sFdfd2WbkuiKGUOFYYzBSqfV0U+f6v/DxSWQMqqrg0S8sABDF1ynvOcNZtbGyNk0J15IMnZbVjr7AN8uIbMMh3I+WHEa/T3Zl8NZmBzBoNLYziwX9qVD9kpIX4df2xkfN6xkvymUpTj1burogWxmT4NPxLPn2cZE1a//0ztvz5Nfh6xQIDAQAB";

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64EncodedPrivateKey));
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64EncodedPublicKey));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        JWT jwt = new JWT.Builder().rs256().claims("user", "fengwk").build(privateKey);
        System.out.println(jwt);

        assert new JWT.Parser(jwt.toString(), publicKey).validate();

        assert jwt.claims("user", String.class).equals("fengwk");
    }

    @Test
    public void testClaims() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String base64EncodedPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHexZRzwBW+b6iVSgA1SSOMXTWYjnsVyK+cDnV56wpomA9+I2jJX6TmxV6tv+BVwzqT6B7LLDdZBDAPryHJuF9RurG2pzM10HN7+h69IkxMiQjFu+JaXUaLOLFFWSsBaFpwMH/TXkF6lLA7YwpKQNkgbhRJb9c8n2VjIfnawV193ZZuS6IoZQ4VhjMFKp9XRT5/q/8PFJZAyqquDRLywAEMXXKe85w1m1sbI2TQnXkgydltWOvsA3y4hswyHcj5YcRr9PdmXw1mYHMGg0tjOLBf2pUP2Skhfh1/bGR83rGS/KZSlOPVu6uiBbGZPg0/Es+fZxkTVr//TO2/Pk1+HrFAgMBAAECggEBAK2dXEShZAtO2/MPKHzpBSLYunaoEP+4qt2ScVPRic7Gqvrkq+lVhb/UFzKxuNkKOYxo9ySdNeXJ5KZdM9pxJYnSQh4GQLgrlPM2nWD+GbV+jWMY4Cv5Y9j/PfMH9pGdaOnQwUeBVxal5ZZSaU2di31bRV8i+lVcG58gK1xnkd4jlBOBdgKd9zgyUljFzWQA5Q6R6aRsVWyLXfLjhRnisPXNUvO1WUE4cTzxt7wkwjh2GIDIi7X6SxpKZX5z+h0NjeX9dgpc8xCQA2AoaC2w7dZp1vL40J1oCP5bS5GqVgI7NGScGFc8pVYdWl8e5yKpaQzwDaJaK2YmXgZVfbxuycECgYEA6FWwF4ByoBUip8jPsoWkBSdv0C8ILFg+0XUifkpc5kC2n1tCg5nXhhFJecx6wJ1CxJFj3H/DalyH5ddaOYWCPcF8pl3AqS50l6mbKt1azx0KCWI83J749hm/WCOoQ86grhLKwqp11hFyro/9XjTMP++r8xT8RDhnyZkJNTR7jbUCgYEA28y1JCMUv3/qNKxuj0TQj8A3E3DYtRl2NkEgUZqLZBuSdbyqZOHN96DaDUhh5aoGhBLdflfbATLcdKfM+7AChHb52bFdZw75xlKudP2DqPrVpL+wkgnLL7T3+IuPvvAozHM3CZ++Z5ROYc5IXy91f+wPpQ2hA+Rbt+31QWIA4tECgYAEWKYEqoh6airNzLnl8w4Qcp5q3JZgjf4O2QweJLH8NLW14XUrFROtFGG1f5fERqzbFIUzuMglEzoPmKm9bbvz/8yfKB3UjGPt+mRGtwFd4oMvjcDpt6ecovqrYW9iZVkoKH3Wim3N5WGp1lO9EWvIvmkPERNvC0cVxGEht6ugCQKBgQCqsxuGuhv18VChobynVYkn6t6bIYubce/FiOShm4VftK4U8/XdLSR5iRlvMWSeKWKwFbqg0XhLQQXHLz5ZxULfP0shDsb8L7w5kqLcRrqGdG+RaDGn6SU8oq3l//UeE4OL7Hq05bPI23vYJ407Jy94rosj4ybGUAYPHSXYplUL0QKBgD/GYm927mwQrpKqDJoCD4irUJFdt44UMLRGfgiuhbjtMkxMeUPycRTaMuVn/OZ3juol2JuzzNNYN0j0SaT0/NpA0IdvKy4NyX09oRwxP1igKoDjY+OiA5yvjutQXTIY72FWje41hk9ZC5yWi5HJyW9RHWW7X1fLZBg5zJ12lRi8";
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx3sWUc8AVvm+olUoANUkjjF01mI57FcivnA51eesKaJgPfiNoyV+k5sVerb/gVcM6k+geyyw3WQQwD68hybhfUbqxtqczNdBze/oevSJMTIkIxbviWl1GizixRVkrAWhacDB/015BepSwO2MKSkDZIG4USW/XPJ9lYyH52sFdfd2WbkuiKGUOFYYzBSqfV0U+f6v/DxSWQMqqrg0S8sABDF1ynvOcNZtbGyNk0J15IMnZbVjr7AN8uIbMMh3I+WHEa/T3Zl8NZmBzBoNLYziwX9qVD9kpIX4df2xkfN6xkvymUpTj1burogWxmT4NPxLPn2cZE1a//0ztvz5Nfh6xQIDAQAB";

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64EncodedPrivateKey));
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64EncodedPublicKey));

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        JWT jwt = new JWT.Builder().rs256().claims("user", "fengwk").exp(100).build(privateKey);

        JWT newJwt = new JWT.Parser(jwt.toString(), publicKey).parse();

        assert newJwt.claims("user", String.class).equals("fengwk");
        assert newJwt.exp() == 100;
    }

}
