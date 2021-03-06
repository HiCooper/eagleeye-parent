package com.berry.eagleeye.bridge.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2021/1/5
 * fileName：ApprovalResultDocument
 * Use：
 */
@Data
@Document("task_result")
public class TaskResultDocument {

    /**
     * 记录ID 作为 文档主键
     */
    @Id
    private String recordId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 处理结果
     */
    private Object taskResult;
}
