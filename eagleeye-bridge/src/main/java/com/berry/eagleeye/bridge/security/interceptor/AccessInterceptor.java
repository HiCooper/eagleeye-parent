package com.berry.eagleeye.bridge.security.interceptor;

import com.berry.eagleeye.bridge.common.constant.Constants;
import com.berry.eagleeye.bridge.common.utils.NetworkUtils;
import com.berry.eagleeye.bridge.security.SecurityUtils;
import com.berry.eagleeye.bridge.security.dto.UserInfoDTO;
import com.berry.eagleeye.bridge.service.CommitOnceCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019-06-30 14:54
 * fileName：AccessInterceptor
 * Use：
 */
public class AccessInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);

    private final CommitOnceCheckService commitOnceCheckService;
    private final AccessProvider accessProvider;

    public AccessInterceptor(CommitOnceCheckService commitOnceCheckService, AccessProvider accessProvider) {
        this.commitOnceCheckService = commitOnceCheckService;
        this.accessProvider = accessProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IllegalAccessException, UnsupportedEncodingException {
        String requestUrl = request.getRequestURI();
        if (Constants.WRITE_LIST.stream().noneMatch(requestUrl::matches)) {
            // 走到这的请求，必须已有 用户上下文
            long expire = commitOnceCheckService.checkRequestOnceCommit(request);
            if (expire != 0) {
                throw new AccessDeniedException("操作太频繁，请" + expire + "秒后再试！");
            }

            // 验证用户 上下文
            UserInfoDTO currentUser = SecurityUtils.getCurrentUser();
            if (currentUser == null) {
                throw new AccessDeniedException("身份核验失败");
            }

            // sdk 通用请求拦截器,sdk token
            String bridgeAuth = getBridgeAuthToken(request);
            String ip = NetworkUtils.getRequestIpAddress(request);
            if (isNotBlank(bridgeAuth)) {
                // 验证 token 合法性
                this.accessProvider.validateSdkAuthentication(request);
                logger.info("IP:{} 通过 sdk 授权认证", ip);
            }
        }
        return true;
    }

    private String getBridgeAuthToken(HttpServletRequest request) {
        // 优先从url参数获取，再从请求头获取
        String token = request.getParameter("token");
        if (isNotBlank(token)) {
            return token;
        }
        return request.getHeader(Constants.BRIDGE_SDK_AUTH_HEAD_NAME);
    }
}
