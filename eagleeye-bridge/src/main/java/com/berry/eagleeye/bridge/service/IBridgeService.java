package com.berry.eagleeye.bridge.service;

import com.berry.eagleeye.module.ApprovalResultDto;
import com.berry.eagleeye.module.RecordStatusVo;
import com.berry.eagleeye.module.SubmitApprovalRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2020/12/9
 * fileName：Bridge
 * Use：
 */
public interface IBridgeService {

    /**
     * 提交审批
     * @param request 请求参数对象
     * @param httpServletRequest httpServletRequest
     * @return 提交ID
     */
    String submitApproval(SubmitApprovalRequest request, HttpServletRequest httpServletRequest);

    /**
     * 查询 记录当前 状态
     *
     * @param submitId 提交ID
     * @return 记录状态信息
     */
    RecordStatusVo getStatus(Long submitId);

    /**
     * 获取审批结果
     *
     * @param submitId 提交ID
     * @return 审批结果信息
     */
    ApprovalResultDto getResult(Long submitId);

    /**r
     * 终止审批
     * @param submitId 提交ID
     */
    void cancelApproval(Long submitId);
}
