package com.berry.eagleeye.sdk.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2021/1/6
 * fileName：UrlFactory
 * Use：
 */
public class UrlFactory {

    public enum BridgeUrl {
        /**
         * url
         */
        submit_approval("/api/bridge/v1/submit_approval", "提交预审", "POST"),
        get_status("/api/bridge/v1/get_status?recordId=%s", "查询审批状态", "GET"),
        get_result("/api/bridge/v1/get_result?recordId=%s", "获取审批结果", "GET");

        private String url;
        private String desc;
        private String method;

        BridgeUrl(String url, String desc, String method) {
            this.url = url;
            this.desc = desc;
            this.method = method;
        }

        public String getUrl() {
            return url;
        }

        public String getDesc() {
            return desc;
        }

        public String getMethod() {
            return method;
        }
    }
}
