package com.berry.eagleeye.bridge.mongo;

import com.alibaba.fastjson.JSON;
import com.berry.eagleeye.bridge.mongo.document.ApprovalResultDocument;
import com.berry.eagleeye.bridge.mongo.document.RequestDocument;
import com.berry.eagleeye.bridge.mongo.dto.FormatErrorMessageDocument;
import com.berry.eagleeye.module.ApprovalResultDto;
import com.berry.eagleeye.module.FailResultDto;
import com.berry.eagleeye.module.SubmitApprovalRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2020/12/9
 * fileName：MongoService
 * Use：
 */
@Service
public class MongoService {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 保存请求参数
     * @param request request params
     * @param username username
     * @param sourceIp ip
     */
    public void saveRequestParams(SubmitApprovalRequest request, String sourceIp) {
        RequestDocument requestDocument = new RequestDocument();
        requestDocument.setCreateTime(new Date());
        requestDocument.setRecordId(request.getRecordId());
        requestDocument.setSourceIp(sourceIp);
        requestDocument.setParams(request);
        mongoTemplate.save(requestDocument);
    }

    /**
     * 保存预审结果
     *
     * @param approvalResultDto approvalResult
     */
    public void saveApprovalResult(ApprovalResultDto approvalResultDto) {
        ApprovalResultDocument approvalResultDocument = new ApprovalResultDocument();
        approvalResultDocument.setRecordId(approvalResultDto.getRecordId());
        approvalResultDocument.setCreateTime(new Date());
        approvalResultDocument.setApprovalResult(approvalResultDto);
        mongoTemplate.save(approvalResultDocument);
    }

    /**
     * 保存格式解析异常消息
     * @param message msg
     */
    public void saveFormatErrorMsg(String message, String type) {
        FormatErrorMessageDocument formatErrorMessageDocument = new FormatErrorMessageDocument(message, type);
        mongoTemplate.save(formatErrorMessageDocument);
    }

    /**
     * 查询预审结果
     *
     * @param recordId recordId
     * @return approvalResultDto
     */
    public ApprovalResultDto getResult(String recordId) {
        Query query = new Query(Criteria.where("recordId").is(recordId));
        ApprovalResultDocument document = mongoTemplate.findOne(query, ApprovalResultDocument.class);
        if (document == null || document.getApprovalResult() == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(document.getApprovalResult()), ApprovalResultDto.class);
    }

    public void saveApprovalFailResult(FailResultDto failResultDto) {
        failResultDto.setCreateTime(new Date());
        mongoTemplate.save(failResultDto, "fail_result");
    }
}
