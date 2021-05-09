package com.berry.eagleeye.management.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties for whole project.
 * <p>
 * Properties are configured in the application.yml file.
 *
 * @author xueancao
 */
@Component
@ConfigurationProperties(prefix = "global")
public class GlobalProperties {

    private final CorsConfiguration cors = new CorsConfiguration();

    private boolean openRedisQueueScan;

    public CorsConfiguration getCors() {
        return cors;
    }

    private final Oss oss = new Oss();

    public boolean isOpenRedisQueueScan() {
        return openRedisQueueScan;
    }

    public void setOpenRedisQueueScan(boolean openRedisQueueScan) {
        this.openRedisQueueScan = openRedisQueueScan;
    }

    public static class Oss {
        /**
         * 密钥key
         */
        private String accessKeyId;
        /**
         * 密钥私钥
         */
        private String accessKeySecret;
        /**
         * oss 服务 ip:port
         */
        private String serverHost;

        /**
         * 当前默认操作 存储空间
         */
        private String bucket;

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getServerHost() {
            return serverHost;
        }

        public void setServerHost(String serverHost) {
            this.serverHost = serverHost;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }
    }

    public Oss getOss() {
        return oss;
    }
}
