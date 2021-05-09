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
 * fileName：RequestDocument
 * Use：
 */
@Data
@Document("request_params")
public class RequestDocument {

    /**
     * 审批记录主键 作为 文档主键
     */
    @Id
    private String recordId;

    /**
     * 请求IP
     */
    private String sourceIp;

    /**
     * 时间
     */
    private Date createTime;

    /**
     * 请求参数
     */
    private Object params;
}
