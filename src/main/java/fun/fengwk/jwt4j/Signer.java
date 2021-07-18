package fun.fengwk.jwt4j;

import java.security.Key;

/**
 * 签名器。
 *
 * @author fengwk
 */
public interface Signer {

    /**
     * 获取当前签名器支持的算法。
     *
     * @return
     */
    String alg();

    /**
     * 对字符串进行签名。
     *
     * @param encodedHeaderAndPayload
     * @param key
     * @return
     * @throws SignException 如果前面过程发生错误将抛出该异常
     */
    byte[] sign(byte[] encodedHeaderAndPayload, Key key);

}
