package fun.fengwk.jwt4j;

import java.lang.reflect.Type;

/**
 * @author fengwk
 */
public interface JsonUtils {

    /**
     * 将映射对象转为json。
     *
     * @param obj
     * @return
     */
    String toJson(Object obj);

    /**
     * 将json转为映射对象。
     *
     * @param json
     * @return
     * @throws JsonParseException json解析过程发生错误抛出该异常
     */
    <T> T fromJson(String json, Type type);

}
