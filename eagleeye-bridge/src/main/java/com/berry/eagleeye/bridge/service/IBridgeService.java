package com.berry.eagleeye.bridge.service;

import com.berry.eagleeye.bridge.module.mo.SubmitRequest;

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
     * 提交任务处理
     *
     * @param request            请求参数对象
     * @param httpServletRequest httpServletRequest
     * @return 提交ID
     */
    String submitApproval(SubmitRequest request, HttpServletRequest httpServletRequest);

    /**
     * r
     * 终止任务处理
     *
     * @param submitId 提交ID
     */
    void cancelApproval(Long submitId);
}
