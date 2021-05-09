package com.berry.eagleeye.bridge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Title CommitOnceFilter
 * Description 防止重复提交过滤器
 *
 * @author berry_cooper
 * @version 1.0
 * @date 2019/8/13 12:21
 */
@Component
public class CommitOnceCheckService {
    private final Logger logger = LoggerFactory.getLogger(CommitOnceCheckService.class);

    private static final String REQUEST_ID = "requestId";

    private static final String GET = "GET";

    @Resource
    private RedisSetOperationService redisSetOperationService;

    public long checkRequestOnceCommit(HttpServletRequest request) {
        if (request.getMethod().equals(GET)) {
            return 0;
        }
        String requestId = request.getHeader(REQUEST_ID);
        if (isBlank(requestId)) {
            logger.error("request head lost requestId field");
            throw new AccessDeniedException("请求头缺少 requestId");
        }
        return redisSetOperationService.checkRequestIdExist(requestId);
    }
}
