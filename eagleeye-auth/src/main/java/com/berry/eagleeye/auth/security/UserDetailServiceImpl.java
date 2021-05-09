package com.berry.eagleeye.auth.security;

import com.berry.eagleeye.auth.common.exceptions.BaseException;
import com.berry.eagleeye.auth.security.dao.entity.RoleInfo;
import com.berry.eagleeye.auth.security.dao.entity.UserInfo;
import com.berry.eagleeye.auth.security.dao.service.IUserDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2018-12-02 11:51
 * fileName：UserDetailService
 * Use：
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Resource
    private IUserDaoService userDaoService;

    /**
     * 根据用户名获取用户并创建授权用户信息
     * success->创建用户
     * fail-> 抛出异常 Bad credentials
     *
     * @param username 用户名
     * @return 安全用户详情
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating {}", username);
        String lowercaseLogin = username.toLowerCase(Locale.CHINA);
        Optional<UserInfo> oneByUsername = userDaoService.findOneByUsername(lowercaseLogin);
        return oneByUsername.map(user -> createSpringSecurityUser(lowercaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    /**
     * 创建授权用户
     *
     * @param username 用户名
     * @param userInfo     用户基本信息
     * @return 安全用户
     */
    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String username, UserInfo userInfo) {
        if (!userInfo.isActivated()) {
            throw new UserNotActivatedException("User " + username + " was not activated");
        }
        Set<RoleInfo> roleInfoList = userDaoService.findRoleListByUserId(userInfo.getId());
        if (roleInfoList == null) {
            throw new BaseException("403", "用户尚未分配角色");
        }
        List<GrantedAuthority> grantedAuthorities = roleInfoList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(userInfo.getUsername(),
                userInfo.getPassword(), userInfo.isEnabled(), userInfo.getExpired() == null || userInfo.getExpired().after(new Date()), true, !userInfo.isLocked(),
                grantedAuthorities);
    }
}
