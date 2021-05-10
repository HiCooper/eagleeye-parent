package com.berry.eagleeye.bridge.mongo;

import com.berry.eagleeye.bridge.module.dto.FailResultDto;
import com.berry.eagleeye.bridge.module.dto.TaskMessageDTO;
import com.berry.eagleeye.bridge.module.mo.SubmitRequest;
import com.berry.eagleeye.bridge.mongo.document.RequestDocument;
import com.berry.eagleeye.bridge.mongo.document.TaskResultDocument;
import com.berry.eagleeye.bridge.mongo.dto.FormatErrorMessageDocument;
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
     *
     * @param request  request params
     * @param sourceIp ip
     */
    public void saveRequestParams(SubmitRequest request, String sourceIp) {
        RequestDocument requestDocument = new RequestDocument();
        requestDocument.setCreateTime(new Date());
        requestDocument.setRecordId(request.getRecordId());
        requestDocument.setSourceIp(sourceIp);
        requestDocument.setParams(request);
        mongoTemplate.save(requestDocument);
    }

    /**
     * 保存任务处理结果
     *
     * @param TaskMessageDTO taskMessageDTO
     */
    public void saveApprovalResult(TaskMessageDTO taskMessageDTO) {
        TaskResultDocument taskResultDocument = new TaskResultDocument();
        taskResultDocument.setRecordId(taskMessageDTO.getRecordId());
        taskResultDocument.setCreateTime(new Date());
        taskResultDocument.setTaskResult(taskMessageDTO);
        mongoTemplate.save(taskResultDocument);
    }

    /**
     * 保存格式解析异常消息
     *
     * @param message msg
     */
    public void saveFormatErrorMsg(String message, String type) {
        FormatErrorMessageDocument formatErrorMessageDocument = new FormatErrorMessageDocument(message, type);
        mongoTemplate.save(formatErrorMessageDocument);
    }

    /**
     * 查询任务处理结果
     *
     * @param recordId recordId
     * @return approvalResultDto
     */
    public Object getResult(String recordId) {
        Query query = new Query(Criteria.where("recordId").is(recordId));
        TaskResultDocument document = mongoTemplate.findOne(query, TaskResultDocument.class);
        if (document == null || document.getTaskResult() == null) {
            return null;
        }
        return document.getTaskResult();
    }

    public void saveApprovalFailResult(FailResultDto failResultDto) {
        failResultDto.setCreateTime(new Date());
        mongoTemplate.save(failResultDto, "fail_result");
    }
}
