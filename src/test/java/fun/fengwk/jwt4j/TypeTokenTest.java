package fun.fengwk.jwt4j;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author fengwk
 */
public class TypeTokenTest {

    @Test
    public void test() {
        Type type1 = new TypeToken<Map<String, Object>>() {}.getType();
        Type type2 = new TypeToken<Map<String, Object>>() {}.getType();
        Type type3 = new TypeToken<Map<String, Object>>() {}.getType();

        assert type1 == type2;
        assert type2 == type3;
    }

}
