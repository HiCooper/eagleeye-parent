package com.berry.eagleeye.management.common.utils;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xueancao
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

    private static Random random;

    static {
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            logger.error("init random fail...");
        }
    }

    /**
     * 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）&拼接成字符
     *
     * @param paramsMap map
     * @return urlStr
     */
    public static String parseUrlParams(StringMap paramsMap) {
        List<Map.Entry<String, Object>> infoIds = new ArrayList<>(paramsMap.entrySet());
        infoIds.sort(Comparator.comparing(Map.Entry::getKey));
        // 构造URL 键值对的格式
        StringBuilder buf = new StringBuilder();
        for (Map.Entry<String, Object> item : infoIds) {
            if (StringUtils.isNotBlank(item.getKey()) && item.getValue() != null) {
                String key = item.getKey();
                String val = item.getValue().toString();
                buf.append(key).append("=").append(val);
                buf.append("&");
            }
        }
        String buff = buf.toString();
        if (!buff.isEmpty()) {
            buff = buff.substring(0, buff.length() - 1);
        }
        return buff;
    }

    public static String getUrlParams(String barcode, String key) {
        if (org.apache.commons.lang3.StringUtils.isBlank(barcode) || !barcode.contains("?")) {
            return null;
        }
        String[] paramsArr = barcode.substring(barcode.indexOf("?") + 1).split("&");
        Map<String, String> result = Maps.newHashMap();
        for (String item : paramsArr) {
            String[] param = item.split("=");
            result.put(param[0], param[1]);
        }
        return result.get(key);
    }

    /**
     * 随机生成指定长度位字符串
     *
     * @return 指定长度位字符串
     */
    public static String getRandomNumStr(int length) {
        String base = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机生成指定长度位字符串
     *
     * @return 指定长度位字符串
     */
    public static String getRandomStr(int length) {
        String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static byte[] utf8Bytes(String data) {
        return data.getBytes(StandardCharsets.UTF_8);
    }

    public static String utf8String(byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
    }

    /**
     * 将特殊字符 替换为 '-'
     *
     * @param str
     * @return
     */
    public static String filterUnsafeUrlCharts(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        String regEx = "[/<>\"#%{}^\\[\\]`\\s\\\\]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("-").trim();
    }


    /**
     * 全角转半角:
     *
     * @param fullStr
     * @return
     */
    public static String toDBC(String fullStr) {
        if (isEmpty(fullStr)) {
            return fullStr;
        }
        char[] c = fullStr.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 65281 && c[i] <= 65374) {
                c[i] = (char) (c[i] - 65248);
            } else if (c[i] == 12288) {
                // 空格
                c[i] = (char) 32;
            }
        }
        return new String(c);
    }
}
