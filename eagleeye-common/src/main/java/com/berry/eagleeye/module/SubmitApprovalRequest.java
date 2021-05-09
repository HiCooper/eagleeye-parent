package com.berry.eagleeye.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2020/12/9
 * fileName：SubmitApprovalRequest
 * Use：提交审批 请求参数体
 */
@Data
public class SubmitApprovalRequest {

    @JsonProperty("routing_key_suffix")
    @JSONField(name = "routing_key_suffix")
    private String routingKeySuffix;

    /**
     * 版本
     **/
    private String version;

    /**
     * 事项编号
     */
    private String sid;

    /**
     * 记录ID
     */
    @JsonProperty("record_id")
    @JSONField(name = "record_id")
    private String recordId;

    /**
     * 调用模式
     */
    @JsonProperty("calc_mode")
    @JSONField(name = "calc_mode")
    private String calcMode;

    /**
     * 材料图片列表
     */
    @JsonProperty("image_list")
    @JSONField(name = "image_list")
    private List<ImageItem> imageList;

    /**
     * 附加信息
     * 如：证照结构化数据
     */
    @JsonProperty("extra_info")
    @JSONField(name = "extra_info")
    private ExtraInfo extraInfo;

    /**
     * 项目ID
     */
    @JsonProperty("project_id")
    @JSONField(name = "project_id")
    private String projectId;

    /**
     * 项目的其他信息
     */
    @JsonProperty("project_info")
    @JSONField(name = "project_info")
    private Map<String, Object> projectInfo;

    @Data
    public static class ImageItem {
        /**
         * 图片唯一ID
         */
        @JsonProperty("image_id")
        @JSONField(name = "image_id")
        private String imageId;

        /**
         * 图片URL
         */
        @JsonProperty("image_url")
        @JSONField(name = "image_url")
        private String imageUrl;

        /**
         * 材料名称
         */
        @JsonProperty("document_name")
        @JSONField(name = "document_name")
        private String documentName;

        /**
         * 材料分类标签
         */
        @JsonProperty("document_label")
        @JSONField(name = "document_label")
        private String documentLabel;

        /**
         * 方向矫正后图片URL
         */
        @JsonProperty("corrected_image_url")
        @JSONField(name = "corrected_image_url")
        private String correctedImageUrl;

        /**
         * 是否包含标题
         */
        @JsonProperty("is_with_title")
        @JSONField(name = "is_with_title")
        private String isWithTitle;

        /**
         * 当前页
         */
        @JsonProperty("document_page")
        @JSONField(name = "document_page")
        private String documentPage;

        /**
         * 材料所属分类 总页数
         */
        @JsonProperty("total_pages")
        @JSONField(name = "total_pages")
        private String totalPages;
    }

    @Data
    public static class ExtraInfo {

        /**
         * AI 自动识别子项
         * 开关 0：关闭， 1-打开
         */
        @JsonProperty("ai_subkey_recognition_flag")
        @JSONField(name = "ai_subkey_recognition_flag")
        private Integer aiSubKeyRecognitionFlag;

        /**
         * 子项列表
         * 当且仅当 {@aiSubKeyRecognitionFlag} = 0 使用此项
         */
        @JsonProperty("subkey_set")
        @JSONField(name = "subkey_set")
        private List<String> subKeySet;

        /**
         * 结构化信息 列表
         */
        @JsonProperty("extra_kv_info")
        @JSONField(name = "subkey_set")
        private List<ExtraKvInfoItem> extraKvInfo;
    }

    @Data
    public static class ExtraKvInfoItem {

        /**
         * 来源 （扫描件, 超级帮办, 电子证照）
         * <br />
         * 默认：扫描件
         */
        private String source;

        /**
         * 是否使用该附加信息
         * <ul><li>
         * 0 不使用
         * </li><li>
         * 1 使用
         * </li></ul>
         */
        @JsonProperty("use_flag")
        @JSONField(name = "use_flag")
        private int useFlag;

        @JsonProperty("subkey_set")
        @JSONField(name = "subkey_set")
        private List<DocumentItem> documentList;
    }

    @Data
    public static class DocumentItem {
        /**
         * 图片唯一ID
         */
        @JsonProperty("image_id")
        @JSONField(name = "image_id")
        private String imageId;

        /**
         * 图片分类标签
         */
        @JsonProperty("document_label")
        @JSONField(name = "document_label")
        private String documentLabel;

        /**
         * 结构化数据 键值对
         * 值是 list 类型
         */
        @JsonProperty("field_val")
        @JSONField(name = "field_val")
        private List<Map<String, Object>> fieldVal;
    }

}
