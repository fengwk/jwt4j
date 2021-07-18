package fun.fengwk.jwt4j;

import java.security.Key;

/**
 * 验证器。
 *
 * @author fengwk
 */
public interface Validator {

    /**
     * 获取当前验证器支持的算法。
     *
     * @return
     */
    String alg();

    /**
     * 对令牌进行验证。
     *
     * @param headerAndPayload
     * @param signature
     * @param key
     * @return
     * @throws ValidateException 如果验证过程发生错误将抛出该异常
     */
    boolean validate(byte[] headerAndPayload, byte[] signature, Key key);

}
