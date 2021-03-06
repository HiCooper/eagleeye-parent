package com.berry.eagleeye.sdk.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.security.GeneralSecurityException;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019-06-29 11:03
 * fileName：Auth
 * Use：提供签名授权
 */
public final class Auth {

    /**
     * 加密json 中 请求ip key
     */
    private static final String ENCODE_JSON_REQUEST_IP_KEY = "requestIp";
    /**
     * 加密json 中 token过期时间 key
     */
    private static final String ENCODE_JSON_DEADLINE_KEY = "deadline";

    private static final String MAC_ALGORITHM = "HmacSHA1";
    public static final String BRIDGE_SDK_AUTH_HEAD_NAME = "bridge_sdk_authorization";
    private static final String SDK_REQUEST_TOKEN_PREFIX = "BRIDGE-";

    private final String accessKeyId;
    private final SecretKeySpec secretKeySpec;

    private Auth(String accessKeyId, SecretKeySpec secretKeySpec) {
        this.accessKeyId = accessKeyId;
        this.secretKeySpec = secretKeySpec;
    }

    public static Auth create(String accessKeyId, String accessKeySecret) {
        if (isBlank(accessKeyId) || isBlank(accessKeySecret)) {
            throw new IllegalArgumentException("empty key");
        }
        byte[] sk = StringUtils.utf8Bytes(accessKeySecret);
        SecretKeySpec secretKeySpec = new SecretKeySpec(sk, MAC_ALGORITHM);
        return new Auth(accessKeyId, secretKeySpec);
    }

    public StringMap authorization(String url) {
        String authorization = SDK_REQUEST_TOKEN_PREFIX + signRequest(url);
        return new StringMap().put(BRIDGE_SDK_AUTH_HEAD_NAME, authorization);
    }

    public String getSign(String url) {
        return SDK_REQUEST_TOKEN_PREFIX + signRequest(url);
    }

    /**
     * 获取授权 口令，
     * <p>注意：该口令拥有对应账户所有bucket的上传权限</p>
     *
     * @param expires 有效时长，单位秒
     * @return token
     */
    public String uploadToken(long expires, String requestIp) {
        long deadline = System.currentTimeMillis() / 1000 + expires;
        // 这里保存了 过期时间信息, 请求ip
        StringMap map = new StringMap();
        map.put(ENCODE_JSON_DEADLINE_KEY, deadline);
        map.put(ENCODE_JSON_REQUEST_IP_KEY, requestIp);
        // 1.过期时间 和 bucket 转 json 再 base64 编码 得到 encodeJson
        String json = Json.encode(map);
        String encodeJson = Base64Util.encode(StringUtils.utf8Bytes(json));

        // 2.对 encodeJson 进行 mac 加密 再进行 base64 编码 得到 encodedSign
        Mac mac = this.createMac();
        String encodedSign = Base64Util.encode(mac.doFinal(StringUtils.utf8Bytes(encodeJson)));

        // 3.拼接 accessKeyId encodedSign 和 encodeJson，用英文冒号隔开
        return this.accessKeyId + ":" + encodedSign + ":" + encodeJson;
    }

    /**
     * 生成HTTP请求签名字符串
     *
     * @param urlString url
     * @return 签名字符串
     */
    private String signRequest(String urlString) {
        URI uri = URI.create(urlString);
        String path = uri.getRawPath();
        String query = uri.getRawQuery();

        Mac mac = createMac();

        mac.update(StringUtils.utf8Bytes(path));

        if (query != null && query.length() != 0) {
            mac.update((byte) ('?'));
            mac.update(StringUtils.utf8Bytes(query));
        }
        mac.update((byte) '\n');

        String digest = Base64Util.encode(mac.doFinal());

        return this.accessKeyId + ":" + digest;
    }


    private Mac createMac() {
        Mac mac;
        try {
            mac = Mac.getInstance(MAC_ALGORITHM);
            mac.init(secretKeySpec);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return mac;
    }
}
