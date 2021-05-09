package com.berry.eagleeye.bridge.remote.client;

import com.berry.eagleeye.bridge.security.oauth2.OAuth2JwtAccessTokenConverter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @since 2021/3/4
 * fileName：AuthInterceptedFeignConfiguration
 * Use：
 */
@Component
public class Oauth2FeignClientInterceptor implements RequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2FeignClientInterceptor.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER = "Bearer";

    private String token;

    @Resource
    private OAuth2JwtAccessTokenConverter oAuth2JwtAccessTokenConverter;

    @PostConstruct
    public void init() {
        token = tryGetValidatedToken();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 获取 token
        token = tryGetValidatedToken();
        logger.info("add request auth header...");
        requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER, token));
    }

    /**
     * token 会过期，过期了 会自动获取最新的token
     */
    private String tryGetValidatedToken() {
        if (!oAuth2JwtAccessTokenConverter.tryVerifyToken(token)) {
            token = null;
        }
        if (isBlank(token)) {
            // 请求获取token
            token = oAuth2JwtAccessTokenConverter.tryGetAccessToken();
        }
        return token;
    }
}
