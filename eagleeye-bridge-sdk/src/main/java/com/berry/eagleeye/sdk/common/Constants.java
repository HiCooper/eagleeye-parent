package com.berry.eagleeye.sdk.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * Application constants.
 */
public final class Constants {

    public static final String REQUEST_SUCCESS_CODE = "200";

    public static final String isSuccess = "true";

    /**
     * 单个账户最多可创建的密钥对数量 3 个
     */
    public static final int SINGLE_ACCOUNT_ACCESS_KEY_PAIR_MAX = 3;

    /**
     * 文件路径正则
     * 不以 '/' 开头和结尾， 不能出现连续 '//' ，仅允许字母数字中文和单个 '/'
     */
    public static final Pattern FILE_PATH_PATTERN = Pattern.compile("^[^/]((?!//)[a-zA-Z0-9/\\u4E00-\\u9FA5]+)*[^/]$");

    /**
     * url地址正则
     */
    public static final String URL_PATTERN = "^(http[s]?)://[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:/~+#]*[\\w\\-@?^=%&/~+#])?$";

    /**
     * 默认文件路径（根路径 / ）
     */
    public static final String DEFAULT_FILE_PATH = "/";

    /**
     * 所有都是UTF-8编码
     */
    public static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * 上传接口 授权密钥口令 请求头名字，仅可访问 对象 create 方法
     */
    public static final String ACCESS_TOKEN_KEY = "access_token";

    /**
     * access_token 负载信息长度 3
     */
    public static final int ENCODE_DATA_LENGTH = 3;

    /**
     * sdk 授权密钥口令 通用请求头，授权通过的请求，拥有账户的全部权限
     */
    public static final String OSS_SDK_AUTH_HEAD_NAME = "bridge_sdk_authorization";

    /**
     * sdk 口令前缀
     */
    public static final String OSS_SDK_AUTH_PREFIX = "BRIDGE-";

    public static final String VERSION = "1.0.0";

    public static final String JSON_MIME = "application/json";

    public static final String FORM_MIME = "application/x-www-form-urlencoded";

    public static final String MULTIPART_MIME = "multipart/form-data";

    public static final String DEFAULT_MIME = "application/octet-stream";


    public static final String API_SUCCESS_CODE = "200";

    public static final String API_SUCCESS_MSG = "SUCCESS";

    private Constants() {
    }
}
