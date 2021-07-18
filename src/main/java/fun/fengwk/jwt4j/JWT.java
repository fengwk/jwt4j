package fun.fengwk.jwt4j;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.StringTokenizer;

/**
 * @author fengwk
 */
public class JWT {

    private static final String ALG = "alg";

    public static final String ALG_HS256 = "HS256";
    public static final String ALG_RS256 = "RS256";

    /**
     * “iss”(issuer) 发行人。
     */
    private static final String ISSUER = "iss";

    /**
     * “sub”(subject) 主题。
     */
    private static final String SUBJECT = "sub";

    /**
     * “aud”(audience) 接收方。
     */
    private static final String AUDIENCE = "aud";

    /**
     * “exp”(expiration time) 到期时间。
     */
    private static final String EXPIRATION = "exp";

    /**
     * “nbf”(not before) 在此之前不可用。
     */
    private static final String NOT_BEFORE = "nbf";

    /**
     * “iat”(issued at) jwt的签发时间。
     */
    private static final String ISSUED_AT = "iat";

    /**
     * “jti”(JWT ID) JWT的唯一身份标识，例如发生重放攻击的令牌被检测到时可以存储jti在缓存当中进行拦截，这样就避免了存储较长的完整令牌以降存储消耗。
     */
    private static final String ID = "jti";

    public static final String SEPARATOR = ".";

    private static final SignerProvider SIGNER_PROVIDER = new SignerProvider();
    private static final ValidatorProvider VALIDATOR_PROVIDER = new ValidatorProvider();

    private static final Base64.Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder BASE64_URL_DECODER = Base64.getUrlDecoder();

    private static final Type HEADER_TYPE = new TypeToken<Map<String, Object>>() {}.getType();
    private static final Type PAYLOAD_TYPE = new TypeToken<Map<String, Object>>() {}.getType();

    private static volatile JsonUtils jsonUtils;

    /* header */
    private final Map<String, Object> header;

    /* payload */
    private final Map<String, Object> payload;

    /* signature */
    private final String signature;

    private JWT(Map<String, Object> header, Map<String, Object> payload, String signature) {
        this.header = header;
        this.payload = payload;
        this.signature = signature;
    }

    static {
        JsonUtilsInitializer selectedInitializer = null;
        ServiceLoader<JsonUtilsInitializer> sl = ServiceLoader.load(JsonUtilsInitializer.class);
        for (JsonUtilsInitializer initializer : sl) {
            if (selectedInitializer == null || initializer.getOrder() < selectedInitializer.getOrder()) {
                selectedInitializer = initializer;
            }
        }
        selectedInitializer.init();
    }

    /**
     * 注册JWT工具使用的jsonUtils。
     *
     * @param jsonUtils
     */
    public static void registerJsonUtils(JsonUtils jsonUtils) {
        JWT.jsonUtils = jsonUtils;
    }

    /**
     * 获取当前JWT使用的算法。
     *
     * @return
     */
    public String alg() {
        return get(header, ALG, String.class);
    }

    /**
     * 获取当前JWT发行人。
     *
     * @return
     */
    public String iss() {
        return claims(ISSUER, String.class);
    }

    /**
     * 获取当前JWT主题。
     *
     * @return
     */
    public String sub() {
        return claims(SUBJECT, String.class);
    }

    /**
     * 获取当前JWT接收方。
     *
     * @return
     */
    public String adu() {
        return claims(AUDIENCE, String.class);
    }

    /**
     * 获取当前JWT到期时间。
     *
     * @return
     */
    public Long exp() {
        return claims(EXPIRATION, Long.class);
    }

    /**
     * 获取当前JWT在此之前不可用时间。
     *
     * @return
     */
    public Long nbf() {
        return claims(NOT_BEFORE, Long.class);
    }

    /**
     * 获取当前JWT签发时间。
     *
     * @return
     */
    public Long iat() {
        return claims(ISSUED_AT, Long.class);
    }

    /**
     * 获取当前JWT唯一标识。
     *
     * @return
     */
    public String id() {
        return claims(ID, String.class);
    }

    /**
     * 获取指定key的claims值。
     *
     * @param key
     * @return
     */
    public Object claims(String key) {
        return payload.get(key);
    }

    /**
     * 获取指定key的claims值，并将其转为指定类型type，如果claims值与指定的类型不一致将返回null。
     *
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public <T> T claims(String key, Class<T> type) {
        return get(payload, key, type);
    }

    /**
     * 获取指定key的claims值，并将其转为指定类型type，如果claims值与指定的类型不一致将返回null。
     *
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public <T> T claims(String key, Type type) {
        return get(payload, key, type);
    }

    /**
     * 从指定map中获取指定key的value，并将value转为指定类型，如果类型不匹配则返回null
     *
     * @param map
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    private <T> T get(Map<String, Object> map, String key, Type type) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }

        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            if (clazz.isAssignableFrom(value.getClass())) {
                return (T) value;
            }
        }

        // 尝试使用JSON解析
        String json = jsonUtils.toJson(value);
        try {
            return jsonUtils.fromJson(json, type);
        } catch (JsonParseException ignore) {
            return null;
        }
    }

    /**
     * 获取compact表示的JWT令牌。
     *
     * @return
     */
    @Override
    public String toString() {
        String headerJson = jsonUtils.toJson(header);
        String payloadJson = jsonUtils.toJson(payload);

        String encodedHeader = BASE64_URL_ENCODER.encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
        String encodedPayload = BASE64_URL_ENCODER.encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));

        return encodedHeader + SEPARATOR + encodedPayload + SEPARATOR + signature;
    }

    public static class Builder {

        private String alg;
        private Map<String, Object> claims = new HashMap<>();

        /**
         * 指定使用HS256算法生成签名。
         */
        public Builder hs256() {
            alg = ALG_HS256;
            return this;
        }

        /**
         * 指定使用RS256算法生成签名。
         */
        public Builder rs256() {
            alg = ALG_RS256;
            return this;
        }

        /**
         * 指定自定义的签名算法。
         *
         * @param alg
         * @return
         */
        public Builder alg(String alg) {
            this.alg = alg;
            return this;
        }

        /**
         * 指定发行人声明。
         *
         * @param iss
         * @return
         */
        public Builder iss(String iss) {
            claims.put(ISSUER, iss);
            return this;
        }

        /**
         * 指定主题声明。
         *
         * @param sub
         * @return
         */
        public Builder sub(String sub) {
            claims.put(SUBJECT, sub);
            return this;
        }

        /**
         * 指定接收方声明。
         *
         * @param aud
         * @return
         */
        public Builder aud(String aud) {
            claims.put(AUDIENCE, aud);
            return this;
        }

        /**
         * 指定令牌到期时间声明。
         *
         * @param exp 当前时间与UTC时间1970年1月1日午夜之间的差值（以毫秒为单位）
         * @return
         */
        public Builder exp(long exp) {
            claims.put(EXPIRATION, exp);
            return this;
        }

        /**
         * 指定令牌开始生效时间声明。
         *
         * @param nbf 当前时间与UTC时间1970年1月1日午夜之间的差值（以毫秒为单位）
         * @return
         */
        public Builder nbf(long nbf) {
            claims.put(NOT_BEFORE, nbf);
            return this;
        }

        /**
         * 指定jwt的签发时间声明。
         *
         * @param iat 当前时间与UTC时间1970年1月1日午夜之间的差值（以毫秒为单位）
         * @return
         */
        public Builder iat(long iat) {
            claims.put(ISSUED_AT, iat);
            return this;
        }

        /**
         * 指定jwt唯一身份标识声明。
         *
         * @param jti
         * @return
         */
        public Builder jti(String jti) {
            claims.put(ID, jti);
            return this;
        }

        /**
         * 添加声明。
         *
         * @param name
         * @param value
         * @return
         */
        public Builder claims(String name, Object value) {
            claims.put(name, value);
            return this;
        }

        /**
         * 构建JWT令牌实例。
         *
         * @param key
         * @throws IllegalStateException 未指定签名算法或密钥与签名算法不匹配时将抛出该异常
         * @return
         */
        public JWT build(Key key) {
            if (alg == null) {
                throw new IllegalStateException("Must specify alg");
            }

            Signer signer = SIGNER_PROVIDER.getSigner(alg);

            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put(ALG, alg);

            String headerJson = jsonUtils.toJson(headerMap);
            String payloadJson = jsonUtils.toJson(claims);

            String encodedHeader = BASE64_URL_ENCODER.encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
            String encodedPayload = BASE64_URL_ENCODER.encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
            String encodedHeaderAndPayload = encodedHeader + SEPARATOR + encodedPayload;

            byte[] signature;
            try {
                signature = signer.sign(encodedHeaderAndPayload.getBytes(StandardCharsets.US_ASCII), key);
            } catch (SignException e) {
                throw new IllegalStateException(e);
            }

            return new JWT(headerMap, claims, BASE64_URL_ENCODER.encodeToString(signature));
        }

    }

    public static class Parser {

        private JWT jwt;

        public Parser(String jwt, Key key) {
            try {
                this.jwt = tryParse(jwt, key);
            } catch (JsonParseException | ValidateException ignore) {}
        }

        private JWT tryParse(String jwt, Key key) {
            if (jwt == null || jwt.isEmpty()) {
                return null;
            }

            List<String> parts = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(jwt, SEPARATOR);
            while (st.hasMoreTokens()) {
                parts.add(st.nextToken());
            }

            if (parts.size() != 3) {
                return null;
            }

            String encodedHeader = parts.get(0);
            String encodedPayload = parts.get(1);
            byte[] signature = BASE64_URL_DECODER.decode(parts.get(2));

            String headerJson = new String(BASE64_URL_DECODER.decode(encodedHeader), StandardCharsets.UTF_8);
            String payloadJson = new String(BASE64_URL_DECODER.decode(encodedPayload), StandardCharsets.UTF_8);

            Map<String, Object> headerMap = jsonUtils.fromJson(headerJson, HEADER_TYPE);
            if (headerMap == null) {
                return null;
            }
            Object alg0 = headerMap.get(ALG);
            if (alg0 == null || !(alg0 instanceof CharSequence)) {
                return null;
            }
            String alg = alg0.toString();

            Map<String, Object> claims = jsonUtils.fromJson(payloadJson, PAYLOAD_TYPE);
            if (claims == null) {
                return null;
            }

            Validator validator = VALIDATOR_PROVIDER.getValidator(alg);
            if (!validator.validate(
                    (encodedHeader + SEPARATOR + encodedPayload).getBytes(StandardCharsets.US_ASCII),
                    signature,
                    key)) {
                return null;
            }

            return new JWT(headerMap, claims, BASE64_URL_ENCODER.encodeToString(signature));
        }

        /**
         * 验证jwt是否正确。
         *
         * @return
         */
        public boolean validate() {
            return jwt != null;
        }

        /**
         * 解析出JWT对象。
         *
         * @return
         */
        public JWT parse() {
            return jwt;
        }

    }

}
