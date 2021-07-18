package fun.fengwk.jwt4j;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 验证器提供者。
 *
 * @author fengwk
 */
public class ValidatorProvider {

    private final Map<String, Validator> registry;

    public ValidatorProvider() {
        Map<String, Validator> registry = new HashMap<>();
        for (Validator validator : ServiceLoader.load(Validator.class)) {
            registry.put(validator.alg(), validator);
        }
        this.registry = registry;
    }

    /**
     * 获取指定算法的验证器。
     *
     * @param alg
     * @throws IllegalStateException 如果没有支持的指定算法的验证器则抛出该异常
     * @return
     */
    public Validator getValidator(String alg) {
        Validator validator = registry.get(alg);
        if (validator == null) {
            throw new IllegalStateException(String.format("Unsupported validate alg '%s'", alg));
        }
        return validator;
    }

}
