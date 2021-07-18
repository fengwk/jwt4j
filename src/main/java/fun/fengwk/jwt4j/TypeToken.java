package fun.fengwk.jwt4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author fengwk
 */
public abstract class TypeToken<T> {

    private static final ConcurrentMap<Type, Type> TYPE_CACHE = new ConcurrentHashMap<>();

    public Type getType() {
        Type found = null;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (actualTypeArguments.length == 1) {
                found = actualTypeArguments[0];
            }
        }

        if (found == null) {
            return null;
        }

        Type existing = TYPE_CACHE.putIfAbsent(found, found);
        return existing == null ? found : existing;
    }

}
