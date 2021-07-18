package fun.fengwk.jwt4j.support;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import fun.fengwk.jwt4j.JsonParseException;
import fun.fengwk.jwt4j.JsonUtils;

import java.lang.reflect.Type;

/**
 * GsonJsonUtils是提供的JsonUtils默认实现，但并未开启，可以通过配置META-INF/service/fun.fengwk.jwt4j.JsonUtils文件启用该实现。
 *
 * @author fengwk
 */
public class GsonJsonUtilsAdapter implements JsonUtils {

    private final Gson gson;

    public GsonJsonUtilsAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T fromJson(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        } catch (JsonParseException | JsonSyntaxException e) {
            throw new JsonParseException(e);
        }
    }

}
