package com.berry.eagleeye.bridge.security;

import com.berry.eagleeye.bridge.security.interceptor.AccessProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author xueancao
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private CorsFilter corsFilter;

    @Resource
    private Environment environment;

    @Resource
    private AccessProvider accessProvider;

    /**
     * 1.OPTIONS 请求放行
     * 2.静态资源放行
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/i18n/**")
                .antMatchers("/actuator/health")
                .antMatchers("/static/**")
                .antMatchers("/webjars/**");
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> profiles = Arrays.asList(activeProfiles);
        if (profiles.contains("dev")) {
            web.ignoring()
                    .antMatchers("/v2/api-docs")
                    .antMatchers("/swagger-resources/**")
                    .antMatchers("/swagger**");
        }
    }

    /**
     * 禁用默认csrf，自定义登录验证过滤器
     * 防止将头信息添加到响应中
     * 设置session创建策略-从不创建session
     * 定义api访问权限
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .and()
                .headers()
                .frameOptions().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/bridge/**").authenticated()
                .antMatchers("/api/management/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .apply(securityConfigurerAdapter());

    }

    private FilterConfigurer securityConfigurerAdapter() {
        return new FilterConfigurer(accessProvider);
    }
}
