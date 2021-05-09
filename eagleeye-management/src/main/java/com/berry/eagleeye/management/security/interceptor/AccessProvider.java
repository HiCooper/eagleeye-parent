package com.berry.eagleeye.management.security.interceptor;

import com.berry.eagleeye.management.common.constant.Constants;
import com.berry.eagleeye.management.common.utils.Auth;
import com.berry.eagleeye.management.remote.IAuthService;
import com.berry.eagleeye.management.remote.dto.UserRoleDTO;
import com.berry.eagleeye.management.security.SecurityUtils;
import com.berry.eagleeye.management.security.dto.UserInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019-06-29 17:22
 * fileName：AccessProvider
 * Use：
 */
@Component
public class AccessProvider {
    private static final Logger logger = LoggerFactory.getLogger(AccessProvider.class);

    @Resource
    private IAuthService authService;

    public Authentication getSdkAuthentication(String sdkAuth) {
        if (!sdkAuth.startsWith(Constants.BRIDGE_SDK_AUTH_PREFIX)) {
            logger.error("sdk 口令前缀必须为：BRIDGE-， token:{}", sdkAuth);
            return null;
        }
        String dataStr = sdkAuth.substring(4);
        String[] data = dataStr.split(":");
        if (data.length != Constants.ENCODE_SDK_DATA_LENGTH) {
            logger.error("非法token，负载信息长度 2,实际 为:{}", data.length);
            return null;
        }
        return getAuthentication(sdkAuth, data[0]);
    }

    void validateSdkAuthentication(HttpServletRequest request) throws IllegalAccessException, UnsupportedEncodingException {
        UserInfoDTO userInfoDTO = SecurityUtils.getCurrentUser();
        String credentials = SecurityUtils.getCurrentCredentials();
        String path = request.getRequestURI();
        String query = request.getQueryString();
        String urlStr = isBlank(query) ? path : path + "?" + query;
        // token 本身不参与签名，token 必须是在 最后一个参数
        int index = urlStr.indexOf("token=");
        if (index != -1) {
            urlStr = urlStr.substring(0, index - 1);
        }
        // 校验 token 签名
        Auth.validRequest(credentials, URLDecoder.decode(urlStr, "utf-8"), userInfoDTO.getAccessKeyId(), userInfoDTO.getAccessKeySecret());
    }

    private Authentication getAuthentication(String ossAuth, String accessKeyId) {
        // rest-ful 远程调用
        UserInfoDTO principal = authService.getUserInfoDTO(accessKeyId);
        if (principal == null) {
            logger.error("find user by accessKeyId fail, accessKeyId={} ", accessKeyId);
            return null;
        }
        // rest-ful 远程调用
        Set<UserRoleDTO> roleList = authService.findRoleListByUserId(principal.getId());
        List<GrantedAuthority> grantedAuthorities = roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(principal, ossAuth, grantedAuthorities);
    }
}
