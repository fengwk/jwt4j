package fun.fengwk.jwt4j;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author fengwk
 */
public class Base64UrlTest {

    private static final Base64.Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder BASE64_URL_DECODER = Base64.getUrlDecoder();

    @Test
    public void test() {
        byte[] data = "hello fengwk".getBytes(StandardCharsets.UTF_8);
        String encode = BASE64_URL_ENCODER.encodeToString(data);
        byte[] newData = BASE64_URL_DECODER.decode(encode);
        System.out.println(Arrays.equals(data, newData));
    }

}
