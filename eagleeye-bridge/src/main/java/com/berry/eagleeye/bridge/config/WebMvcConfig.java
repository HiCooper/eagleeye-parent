package com.berry.eagleeye.bridge.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.berry.eagleeye.bridge.security.interceptor.AccessInterceptor;
import com.berry.eagleeye.bridge.security.interceptor.AccessProvider;
import com.berry.eagleeye.bridge.service.CommitOnceCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2018-05-03 15:42
 * fileName：WebMvcConfig
 * Use：
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

    private final CommitOnceCheckService commitOnceCheckService;

    private final AccessProvider accessProvider;

    private final GlobalProperties globalProperties;

    public WebMvcConfig(GlobalProperties globalProperties, CommitOnceCheckService commitOnceCheckService, AccessProvider accessProvider) {
        this.globalProperties = globalProperties;
        this.commitOnceCheckService = commitOnceCheckService;
        this.accessProvider = accessProvider;
    }

    /**
     * 添加类型转换器和格式化器
     *
     * @param registry registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(Date.class, new DateFormatter());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.ALL);
        converter.setSupportedMediaTypes(fastMediaTypes);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        converter.setFastJsonConfig(fastJsonConfig);
        converters.add(converter);
    }

    /**
     * 注册拦截器
     * 防止重复提交拦截器， 限制 同一 requestId 频繁访问
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessInterceptor(commitOnceCheckService, accessProvider))
                .addPathPatterns("/api/bridge/**")
                .addPathPatterns("/api/management/**")
                .excludePathPatterns("/index.html", "/", "/ajax/auth/login", "/swagger-resources/**", "/swagger-ui.html");
    }

    @Bean
    @Profile("dev")
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = globalProperties.getCors();
        if (config.getAllowedOrigins() != null && !CollectionUtils.isEmpty(config.getAllowedOrigins())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
        }
        return new CorsFilter(source);
    }
}