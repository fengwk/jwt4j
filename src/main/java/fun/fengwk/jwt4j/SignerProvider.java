package fun.fengwk.jwt4j;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 签名器提供者。
 *
 * @author fengwk
 */
public class SignerProvider {

    private final Map<String, Signer> registry;

    public SignerProvider() {
        Map<String, Signer> registry = new HashMap<>();
        for (Signer signer : ServiceLoader.load(Signer.class)) {
            registry.put(signer.alg(), signer);
        }
        this.registry = registry;
    }

    /**
     * 获取指定算法的签名器。
     *
     * @param alg
     * @throws IllegalStateException 如果没有支持的指定算法的签名器则抛出该异常
     * @return
     */
    public Signer getSigner(String alg) {
        Signer signer = registry.get(alg);
        if (signer == null) {
            throw new IllegalStateException(String.format("Unsupported sign alg '%s'", alg));
        }
        return signer;
    }

}
