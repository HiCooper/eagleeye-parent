package com.berry.eagleeye.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2020/12/9
 * fileName：ApprovalResultVo
 * Use： 审批结果返回体，继承请求参数体
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ApprovalResultDto extends SubmitApprovalRequest {

    @JsonProperty("time_cost")
    private int timeCost = -1;

    /**
     * 分离信息
     */
    @JsonProperty("document_classify")
    private DocumentClassify documentClassify;

    /**
     * 材料提取键值信息
     */
    @JsonProperty("document_kv_info")
    private DocumentKvInfo documentKvInfo;

    /**
     * 审批点对比输出信息
     */
    @JsonProperty("rule_output_data")
    private RuleOutputData ruleOutputData;

    @Data
    public static class DocumentClassify {

        private String version = "100";

        /**
         * 材料分类信息 列表
         */
        @JsonProperty("result_list")
        private List<ImageItem> resultList;
    }

    @Data
    public static class DocumentKvInfo {
        private String version = "100";

        /**
         * 材料内容提取信息 列表（一个记录多份材料）
         */
        @JsonProperty("kv_list")
        private List<KvItem> kvList;
    }

    @Data
    public static class RuleOutputData {

        private String version = "100";

        @JsonProperty("kv_list")
        private List<PointItem> kvList;
    }

    @Data
    public static class PointItem {
        /**
         * 审批点ID（规则ID）
         */
        @JsonProperty("approval_point_id")
        private String approvalPointId;

        /**
         * 审批点名称
         */
        @JsonProperty("approval_point")
        private String approvalPoint;

        /**
         * 审批结论
         */
        @JsonProperty("approval_basis")
        private String approvalBasis;

        /**
         * 审批结论提示信息
         */
        @JsonProperty("approval_message")
        private String approvalMessage;

        /**
         * 审批点 法律依据
         */
        @JsonProperty("law_basis")
        private List<String> lawBasis;

        /**
         * 审批结果状态 （R,F,W）
         * <ul>
         *     <li>R:通过</li>
         *     <li>F:无法判断（需要人工审核）</li>
         *     <li>W:不通过</li>
         * </ul>
         */
        @JsonProperty("approval_result")
        private String approvalResult;

        /**
         * 对比材料列表
         */
        @JsonProperty("document_and_field")
        private List<DocumentAndField> documentAndField;
    }

    /**
     * 审批点 对比材料 信息
     */
    @Data
    public static class DocumentAndField {
        @JsonProperty("image_id")
        private String imageId = "";

        @JsonProperty("document_label")
        private String documentLabel = "";

        @JsonProperty("document_field")
        private String documentField = "";

        @JsonProperty("rule_filter_value_info")
        private List<ValueInfo> ruleFilterValueInfo;

        private String source = "smj";
    }

    @Data
    public static class ValueInfo {
        /**
         * 内容
         */
        @JsonProperty("field_content")
        private String fieldContent;

        /**
         * 坐标
         */
        @JsonProperty("field_location")
        private List<List<Integer>> fieldLocation;

        /**
         * 置信度得分 [0, 1]
         */
        private int score = 0;
    }

    @Data
    public static class KvItem {

        /**
         * 材料分类标签
         */
        @JsonProperty("document_label")
        private String documentLabel = "";

        /**
         * 材料每页 内容提取 （一份材料多页）
         */
        @JsonProperty("document_list")
        private List<KvItemDocumentList> documentList;
    }

    @Data
    public static class KvItemDocumentList {
        /**
         * 每一页的提取信息
         * <br/>
         * 每一个提取点 对应一个元素：PageKvContent
         */
        private List<PageKvContent> content;
    }

    @Data
    public static class PageKvContent {

        @JsonProperty("image_id")
        private String imageId;

        @JsonProperty("document_field")
        private String documentField;

        @JsonProperty("image_or_string")
        private String imageOrString;

        /**
         * 提取内容、坐标、置信度
         */
        @JsonProperty("value_info")
        private List<ValueInfo> valueInfo;

        private String source = "smj";
        @JsonProperty("sort_property")

        private String sortProperty;

        @JsonProperty("display_property")
        private String displayProperty = "WHAT";
    }
}
