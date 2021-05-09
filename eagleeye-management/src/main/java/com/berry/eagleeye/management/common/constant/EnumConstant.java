package com.berry.eagleeye.management.common.constant;


/**
 * @author Berry_Cooper.
 * @date 2018-03-26
 * Description 公共常量
 */
public final class EnumConstant {

    public enum ApprovalResult {

        /**
         * 审批结果
         */
        Fuzzy("F"),
        Wrong("W"),
        Right("R");

        private String type;

        ApprovalResult(String type) {
            this.type = type;
        }

        public static ApprovalResult fromTypeName(String type) {
            for (ApprovalResult item : ApprovalResult.values()) {
                if (item.getType().equals(type)) {
                    return item;
                }
            }
            return null;
        }

        public String getType() {
            return this.type;
        }
    }

    public enum ExecuteState {
        /**
         * 审批结果
         */
        received("已受理"),
        processing("处理中"),
        fail("处理失败"),
        finished("处理完成");

        private String desc;

        ExecuteState(String desc) {
            this.desc = desc;
        }

        public static String getDescByName(String name) {
            for (ExecuteState item : ExecuteState.values()) {
                if (item.name().equals(name)) {
                    return item.getDesc();
                }
            }
            return null;
        }

        public String getDesc() {
            return this.desc;
        }
    }
    public enum NoticeStatus {
        /**
         * 回调通知进行状态
         */
        INIT("已受理"),
        SUCCESS("通知成功"),
        PROCESSING("延迟投递中"),
        FAIL("通知失败");

        private String desc;

        NoticeStatus(String desc) {
            this.desc = desc;
        }

        public static String getDescByName(String name) {
            for (NoticeStatus item : NoticeStatus.values()) {
                if (item.name().equals(name)) {
                    return item.getDesc();
                }
            }
            return null;
        }

        public String getDesc() {
            return this.desc;
        }
    }

    public enum DocSourceType {
        /**
         * 材料来源支持枚举
         */
        SCAN("扫描"),
        FILE("文件"),
        CERT("证照库");

        private String desc;

        public String getDesc() {
            return desc;
        }

        DocSourceType(String desc) {
            this.desc = desc;
        }

        public static String getNameByDesc(String desc) {
            for (DocSourceType item : DocSourceType.values()) {
                if (item.getDesc().equals(desc)) {
                    return item.name();
                }
            }
            return null;
        }

    }

    public enum AttachmentState {
        /**
         * 材料来源支持枚举
         */
        TEMP("临时"),
        CONFIRM("确认");

        private String desc;

        public String getDesc() {
            return desc;
        }

        AttachmentState(String desc) {
            this.desc = desc;
        }

        public static String getNameByDesc(String desc) {
            for (AttachmentState item : AttachmentState.values()) {
                if (item.getDesc().equals(desc)) {
                    return item.name();
                }
            }
            return null;
        }

    }
}