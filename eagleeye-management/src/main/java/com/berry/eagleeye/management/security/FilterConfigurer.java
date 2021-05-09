package com.berry.eagleeye.management.security;

import com.berry.eagleeye.management.security.filter.AuthFilter;
import com.berry.eagleeye.management.security.interceptor.AccessProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author xueancao
 */
public class FilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private AccessProvider accessProvider;

    public FilterConfigurer(AccessProvider accessProvider) {
        this.accessProvider = accessProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
        AuthFilter customFilter = new AuthFilter(accessProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
