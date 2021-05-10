package com.berry.eagleeye.bridge.api;

import com.berry.eagleeye.bridge.common.Result;
import com.berry.eagleeye.bridge.common.ResultFactory;
import com.berry.eagleeye.bridge.module.mo.SubmitRequest;
import com.berry.eagleeye.bridge.service.IBridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @date 2020/12/10
 * fileName：BridgeApi
 * Use：
 */
@RestController
@RequestMapping("/api/bridge/v1")
@PreAuthorize("hasRole('ROLE_USER')")
public class BridgeApi {

    @Autowired
    private IBridgeService bridgeService;

    /**
     * 提交任务处理
     *
     * @param request            请求体
     * @param httpServletRequest request
     * @return submitId
     */
    @PostMapping("/submit_approval")
    public Result<String> submitApproval(@Validated @RequestBody SubmitRequest request, HttpServletRequest httpServletRequest) {
        String submitId = bridgeService.submitApproval(request, httpServletRequest);
        return ResultFactory.wrapper(submitId);
    }

    /**
     * 查询任务处理状态
     *
     * @param submitId 提交任务处理
     * @return RecordStatusVo
     */
    @GetMapping("/get_status")
    public Result getStatus(@Validated @RequestParam("submitId") Long submitId) {
        return ResultFactory.wrapper();
    }

    /**
     * 查询任务处理结果
     *
     * @param submitId 提交ID
     * @return ApprovalResultDto
     */
    @GetMapping("/get_result")
    public Result getResult(@Validated @RequestParam("submitId") Long submitId) {
        return ResultFactory.wrapper();
    }

    /**
     * 终止任务处理
     *
     * @param submitId 提交ID
     * @return ApprovalResultDto
     */
    @GetMapping("/cancel_approval")
    public Result cancelApproval(@Validated @RequestParam("submitId") Long submitId) {
        bridgeService.cancelApproval(submitId);
        return ResultFactory.wrapper();
    }

}
