package com.berry.eagleeye.auth.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2018/10/3 14:58
 * fileName：MybatisPlusConfig
 * Use：
 */
@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = {"com.berry.eagleeye.auth.security.dao.mapper", "com.berry.eagleeye.auth.dao.mapper"})
public class MybatisPlusConfig {

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
