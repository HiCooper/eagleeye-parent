package com.berry.eagleeye.bridge.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2020/5/26 14:17
 * fileName：RestTemplateConfig
 * Use：
 */

@Configuration
public class RestTemplateConfig {

    @Bean
    @Qualifier("loadBalancedRestTemplate")
    public RestTemplate loadBalancedRestTemplate(RestTemplateCustomizer customizer) {
        RestTemplate restTemplate = new RestTemplate();
        customizer.customize(restTemplate);
        return restTemplate;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        //先获取到converter列表
        List<HttpMessageConverter<?>> converters = builder.build().getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            //因为我们只想要jsonConverter支持对text/html的解析
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                //先将原先支持的MediaType列表拷出
                List<MediaType> mediaTypeList = new ArrayList<>(converter.getSupportedMediaTypes());
                //加入对text/html的支持
                mediaTypeList.add(MediaType.TEXT_HTML);
                //将已经加入了text/html的MediaType支持列表设置为其支持的媒体类型列表
                ((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(mediaTypeList);
            }
        }
        return builder.build();
    }

    /**
     * ClientHttpRequestFactory接口的第一种实现方式，即：
     * SimpleClientHttpRequestFactory：底层使用java.net包提供的方式创建Http连接请求
     *
     * @return
     */
    @Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);
        return requestFactory;
    }

}