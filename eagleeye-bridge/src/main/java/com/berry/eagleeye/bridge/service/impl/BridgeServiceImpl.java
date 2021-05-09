package com.berry.eagleeye.bridge.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berry.eagleeye.bridge.common.constant.EnumConstant;
import com.berry.eagleeye.bridge.common.exceptions.BaseException;
import com.berry.eagleeye.bridge.common.utils.NetworkUtils;
import com.berry.eagleeye.bridge.dao.entity.RecordDetail;
import com.berry.eagleeye.bridge.dao.service.IRecordDetailDaoService;
import com.berry.eagleeye.bridge.mongo.MongoService;
import com.berry.eagleeye.bridge.retry.RetryService;
import com.berry.eagleeye.bridge.service.IBridgeService;
import com.berry.eagleeye.module.ApprovalResultDto;
import com.berry.eagleeye.module.RecordStatusVo;
import com.berry.eagleeye.module.SubmitApprovalRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2021/1/5
 * fileName：ApprovalServiceImpl
 * Use：
 */
@Service
public class BridgeServiceImpl implements IBridgeService {
    private final Logger logger = LoggerFactory.getLogger(BridgeServiceImpl.class);

    private static final String DEFAULT_ROUTING_KEY_SUFFIX = "default";

    @Autowired
    private IRecordDetailDaoService recordDetailDaoService;

    @Resource
    private MongoService mongoService;

    @Resource
    private RetryService retryService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String submitApproval(SubmitApprovalRequest request, HttpServletRequest httpServletRequest) {
        String routerKey = isBlank(request.getRoutingKeySuffix()) ? DEFAULT_ROUTING_KEY_SUFFIX : request.getRoutingKeySuffix();
        // 1. 查询记录是否提交
        int count = recordDetailDaoService.count(new QueryWrapper<RecordDetail>().eq("request_record_id", request.getRecordId()));
        if (count != 0) {
            throw new BaseException("403", "记录已提交：" + request.getRecordId());
        }
        String ip = NetworkUtils.getRequestIpAddress(httpServletRequest);
        // 保存请求参数
        mongoService.saveRequestParams(request, ip);

        // 创建记录
        RecordDetail recordDetail = new RecordDetail();
        recordDetail.setRequestRecordId(request.getRecordId());
        recordDetail.setCalcMode(request.getCalcMode());
        recordDetail.setProcessStatus(EnumConstant.ExecuteState.received.name());
        recordDetail.setProjectId(request.getProjectId());
        recordDetail.setRequestVersion(request.getVersion());
        recordDetail.setSid(request.getSid());
        recordDetail.setRoutingKey(routerKey);
        recordDetail.setCreateTime(new Date());
        recordDetailDaoService.save(recordDetail);

        // 发送消息, 如果失败，最多重试3次
        String recordDetailId = recordDetail.getId().toString();
        String msg = JSON.toJSONString(request);
        retryService.sendMsgToAlgo(recordDetailId, msg, routerKey);
        return recordDetailId;
    }

    @Override
    public RecordStatusVo getStatus(Long submitId) {
        // 查询 记录状态表
        RecordDetail recordDetail = getRecordDetail(submitId);
        if (recordDetail == null) {
            throw new BaseException("404", "");
        }
        RecordStatusVo vo = new RecordStatusVo();
        String status = recordDetail.getProcessStatus();
        vo.setSubmitId(submitId);
        vo.setStatus(status);
        vo.setRecordId(recordDetail.getRequestRecordId());
        vo.setStatusDesc(EnumConstant.ExecuteState.getDescByName(status));
        return vo;
    }

    @Override
    public ApprovalResultDto getResult(Long submitId) {
        RecordStatusVo status = getStatus(submitId);
        // 如果状态是：finished(已完成),查询 mongoDB
        if (!EnumConstant.ExecuteState.finished.name().equals(status.getStatus())) {
            throw new BaseException("404", status.getStatusDesc());
        }
        ApprovalResultDto result = mongoService.getResult(String.valueOf(submitId));
        if (result == null) {
            logger.error("异常：记录：{} 状态： {} 。 未找到审批结果", submitId, status.getStatusDesc());
            throw new BaseException("404", "未找到审批结果");
        }
        return result;
    }

    @Override
    public void cancelApproval(Long submitId) {
        // 查询 记录状态表
        RecordDetail recordDetail = getRecordDetail(submitId);
        if (recordDetail == null) {
            throw new BaseException("404", "");
        }
        // 发送消息， 算法停止处理记录
        retryService.stopDealWithRecord(recordDetail.getRequestRecordId(), "", "");

        // 更新状态
        recordDetail.setProcessStatus(EnumConstant.ExecuteState.userCancel.name());
        recordDetailDaoService.updateById(recordDetail);
    }

    /**
     * 查询当前用户的 提交记录详情
     * <br/>
     * <em>不可用 提交ID 直接查询， 需判断 该用户 是否拥有 该 提交ID</em>
     *
     * @param submitId 提交ID
     * @return 提交记录详情
     */
    private RecordDetail getRecordDetail(Long submitId) {
        return recordDetailDaoService.getById(submitId);
    }
}
