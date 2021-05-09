package com.berry.eagleeye.bridge.remote.client;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class OAuth2UserClientFeignConfiguration {

    @Bean(name = "oauth2FeignClientInterceptor")
    public RequestInterceptor getOauth2FeignClientInterceptor() {
        return new Oauth2FeignClientInterceptor();
    }
}
