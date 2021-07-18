# JWT4J

JWT4J是JWT For Java的缩写，该类库是一个出于学习目实现的简单、小巧、可扩展的JWT实现库。

# 使用

JWT4J默认支持HS256和RS256签名算法（这两个签名算法是RFC7518所推荐的），如需使用其它签名算法可以自行通过SPI扩展。

## 依赖

```xml
<dependency>
    <groupId>fun.fengwk.jwt4j</groupId>
    <artifactId>jwt4j</artifactId>
    <version>0.0.1</version>
</dependency>
<!-- 如果使用Gson实现JsonUtils请添加Gson依赖，否则取决于具体JsonUtils实现情况，详见扩展JsonUtils实现 -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>${gson.version}</version>
</dependency>
```

## 使用HS256签名算法

1、使用`java.security`生成HS256所需的密钥。

```java
KeyGenerator gen = KeyGenerator.getInstance(HS256.HMAC_SHA256);
SecretKey secretKey = gen.generateKey();
// 密钥格式
System.out.println(secretKey.getFormat());
// Base64编码的密钥
System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
```

2、使用步骤1生成的密钥进行JWT加密与解密。

```java
// 这里是步骤1生成的密钥
String base64EncodedSecretKey = "hfMEi76AxX59Ky7nzQ4UxoRqnJskF9K0ahTIu7l3wJE=";
// 将密钥按Base64解码，并构建java.security所需的SecretKey对象
SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(base64EncodedSecretKey), HS256.HMAC_SHA256);

// 使用建筑者模式构建JWT，指定算法为hs256，claims可自定义指定
JWT jwt = new JWT.Builder().hs256().claims("user", "fengwk").build(secretKey);
System.out.println(jwt);

// 构建JWT解析器，可用验证jwt以及构建JWT对象
JWT.Parser parser = new JWT.Parser(jwt.toString(), secretKey);
System.out.println(parser.validate());
System.out.println(parser.parse());
```

## 使用RS256签名算法

1、使用`java.security`生成RS256所需的公私钥。

```java
KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
gen.initialize(2048);
KeyPair keyPair = gen.generateKeyPair();

// 如下代码将会输出
// 私钥+私钥格式+私钥的Base64编码形式
// 公钥+公钥格式+公钥的Base64编码形式
System.out.println("privateKey:");
System.out.println(keyPair.getPrivate().getFormat());
System.out.println(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
System.out.println("publicKey:");
System.out.println(keyPair.getPublic().getFormat());
System.out.println(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
```

2、使用步骤1生成的公私钥进行JWT加密与解密。

```java
// 步骤1中生成的公私钥
String base64EncodedPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHexZRzwBW+b6iVSgA1SSOMXTWYjnsVyK+cDnV56wpomA9+I2jJX6TmxV6tv+BVwzqT6B7LLDdZBDAPryHJuF9RurG2pzM10HN7+h69IkxMiQjFu+JaXUaLOLFFWSsBaFpwMH/TXkF6lLA7YwpKQNkgbhRJb9c8n2VjIfnawV193ZZuS6IoZQ4VhjMFKp9XRT5/q/8PFJZAyqquDRLywAEMXXKe85w1m1sbI2TQnXkgydltWOvsA3y4hswyHcj5YcRr9PdmXw1mYHMGg0tjOLBf2pUP2Skhfh1/bGR83rGS/KZSlOPVu6uiBbGZPg0/Es+fZxkTVr//TO2/Pk1+HrFAgMBAAECggEBAK2dXEShZAtO2/MPKHzpBSLYunaoEP+4qt2ScVPRic7Gqvrkq+lVhb/UFzKxuNkKOYxo9ySdNeXJ5KZdM9pxJYnSQh4GQLgrlPM2nWD+GbV+jWMY4Cv5Y9j/PfMH9pGdaOnQwUeBVxal5ZZSaU2di31bRV8i+lVcG58gK1xnkd4jlBOBdgKd9zgyUljFzWQA5Q6R6aRsVWyLXfLjhRnisPXNUvO1WUE4cTzxt7wkwjh2GIDIi7X6SxpKZX5z+h0NjeX9dgpc8xCQA2AoaC2w7dZp1vL40J1oCP5bS5GqVgI7NGScGFc8pVYdWl8e5yKpaQzwDaJaK2YmXgZVfbxuycECgYEA6FWwF4ByoBUip8jPsoWkBSdv0C8ILFg+0XUifkpc5kC2n1tCg5nXhhFJecx6wJ1CxJFj3H/DalyH5ddaOYWCPcF8pl3AqS50l6mbKt1azx0KCWI83J749hm/WCOoQ86grhLKwqp11hFyro/9XjTMP++r8xT8RDhnyZkJNTR7jbUCgYEA28y1JCMUv3/qNKxuj0TQj8A3E3DYtRl2NkEgUZqLZBuSdbyqZOHN96DaDUhh5aoGhBLdflfbATLcdKfM+7AChHb52bFdZw75xlKudP2DqPrVpL+wkgnLL7T3+IuPvvAozHM3CZ++Z5ROYc5IXy91f+wPpQ2hA+Rbt+31QWIA4tECgYAEWKYEqoh6airNzLnl8w4Qcp5q3JZgjf4O2QweJLH8NLW14XUrFROtFGG1f5fERqzbFIUzuMglEzoPmKm9bbvz/8yfKB3UjGPt+mRGtwFd4oMvjcDpt6ecovqrYW9iZVkoKH3Wim3N5WGp1lO9EWvIvmkPERNvC0cVxGEht6ugCQKBgQCqsxuGuhv18VChobynVYkn6t6bIYubce/FiOShm4VftK4U8/XdLSR5iRlvMWSeKWKwFbqg0XhLQQXHLz5ZxULfP0shDsb8L7w5kqLcRrqGdG+RaDGn6SU8oq3l//UeE4OL7Hq05bPI23vYJ407Jy94rosj4ybGUAYPHSXYplUL0QKBgD/GYm927mwQrpKqDJoCD4irUJFdt44UMLRGfgiuhbjtMkxMeUPycRTaMuVn/OZ3juol2JuzzNNYN0j0SaT0/NpA0IdvKy4NyX09oRwxP1igKoDjY+OiA5yvjutQXTIY72FWje41hk9ZC5yWi5HJyW9RHWW7X1fLZBg5zJ12lRi8";
String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx3sWUc8AVvm+olUoANUkjjF01mI57FcivnA51eesKaJgPfiNoyV+k5sVerb/gVcM6k+geyyw3WQQwD68hybhfUbqxtqczNdBze/oevSJMTIkIxbviWl1GizixRVkrAWhacDB/015BepSwO2MKSkDZIG4USW/XPJ9lYyH52sFdfd2WbkuiKGUOFYYzBSqfV0U+f6v/DxSWQMqqrg0S8sABDF1ynvOcNZtbGyNk0J15IMnZbVjr7AN8uIbMMh3I+WHEa/T3Zl8NZmBzBoNLYziwX9qVD9kpIX4df2xkfN6xkvymUpTj1burogWxmT4NPxLPn2cZE1a//0ztvz5Nfh6xQIDAQAB";

// 使用公私钥各自的编码结构构建公私钥对象
PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64EncodedPrivateKey));
X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64EncodedPublicKey));

KeyFactory keyFactory = KeyFactory.getInstance("RSA");
PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

// 使用私钥签名构建jwt
JWT jwt = new JWT.Builder().rs256().claims("user", "fengwk").build(privateKey);
System.out.println(jwt);

// 使用公钥验证
System.out.println(new JWT.Parser(jwt.toString(), publicKey).validate());
```

# 扩展

## 扩展签名器

JWT4J的签名器使用SPI插入，默认提供HS256和RS256实现，如需扩展请实现`fun.fengwk.jwt4j.Signer`接口，并在`META-INF/service/fun.fengwk.jwt4j.Signer`文件中配置实现类信息。

## 扩展校验器

JWT4J的校验器使用SPI插入，默认提供HS256和RS256实现，如需扩展请实现`fun.fengwk.jwt4j.Validator`接口，并在`META-INF/service/fun.fengwk.jwt4j.Validator`文件中配置实现类信息。

## 扩展JsonUtils

JWT4J需要配置JSON解析工具，如果您使用Gson那么添加Gson依赖后框架将自动注入Gson的JsonUtils实现。

当前您也可以使用项目中惯用的JSON解析工具实现`fun.fengwk.jwt4j.JsonUtils`接口，如果您使用的是Gson也可以使用默认提供的适配器`JWT.registerJsonUtils(new GsonJsonUtilsAdapter(gson))`。

将JsonUtils注入JWT的方法有两种：

1、使用SPI（推荐）：可以实现`fun.fengwk.jwt4j.JsonUtilsInitializer`类（可参考`fun.fengwk.jwt4j.support.GsonJsonUtilsInitializer`实现），并在`META-INF/service/fun.fengwk.jwt4j.JsonUtilsInitializer`配置实现类信息。

2、在JWT完成静态初始化后调用`JWT.registerJsonUtils(jsonUtils)`方法将原先JsonUtils覆盖为指定的。