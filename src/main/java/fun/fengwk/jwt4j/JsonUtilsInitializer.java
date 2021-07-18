package fun.fengwk.jwt4j;

/**
 * 用于及时初始化JsonUtils。
 *
 * @author fengwk
 */
public interface JsonUtilsInitializer {

    /**
     * 获取当前初始化器的执行优先级，返回的数字越小优先级越高，最终只会执行优先级最高的初始化器。
     *
     * @return
     */
    int getOrder();

    /**
     * 进行JsonUtils初始化。
     */
    void init();

}
