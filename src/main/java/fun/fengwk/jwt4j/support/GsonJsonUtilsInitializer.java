package fun.fengwk.jwt4j.support;

import com.google.gson.Gson;
import fun.fengwk.jwt4j.JWT;
import fun.fengwk.jwt4j.JsonUtilsInitializer;

/**
 * @author fengwk
 */
public class GsonJsonUtilsInitializer implements JsonUtilsInitializer {

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void init() {
        try {
            Class.forName("com.google.gson.Gson");
            JWT.registerJsonUtils(new GsonJsonUtilsAdapter(new Gson()));
        } catch (ClassNotFoundException ignore) {}
    }

}
