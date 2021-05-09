package com.berry.eagleeye.auth.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.berry.eagleeye.auth.common.Result;
import com.berry.eagleeye.auth.common.ResultCode;
import com.berry.eagleeye.auth.common.ResultFactory;
import com.berry.eagleeye.auth.common.exceptions.BaseException;
import com.berry.eagleeye.auth.module.mo.UserRegisterMo;
import com.berry.eagleeye.auth.security.dao.entity.UserAndRole;
import com.berry.eagleeye.auth.security.dao.service.IUserDaoService;
import com.berry.eagleeye.auth.security.dao.service.IUserRoleDaoService;
import com.berry.eagleeye.auth.security.filter.AuthFilter;
import com.berry.eagleeye.auth.security.filter.TokenProvider;
import com.berry.eagleeye.auth.security.vm.LoginVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

/**
 * @author Berry_Cooper.
 * Description:
 * Date: 2018/05/03
 * fileName MultyTestController
 */
@RestController
@RequestMapping("/api/auth/v1")
@Api(value = "登录授权", tags = "登录授权")
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;

    private final IUserDaoService userDaoService;

    private final IUserRoleDaoService userRoleDaoService;

    private final TokenProvider tokenProvider;

    public AuthController(IUserDaoService userDaoService, IUserRoleDaoService userRoleDaoService, TokenProvider tokenProvider) {
        this.userDaoService = userDaoService;
        this.userRoleDaoService = userRoleDaoService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseEntity<LoginSuccessVo> authorize(@Valid @RequestBody LoginVM loginVm, HttpServletResponse response) {
        // 用户是否存在
        com.berry.eagleeye.auth.security.dao.entity.UserInfo userInfo = userDaoService.getOne(new QueryWrapper<com.berry.eagleeye.auth.security.dao.entity.UserInfo>().eq("username", loginVm.getUsername()));
        if (userInfo == null) {
            throw new BaseException(ResultCode.ACCOUNT_NOT_EXIST);
        }
        // 密码是否正确
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVm.getUsername(), loginVm.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = this.tokenProvider.createAndSignToken(authentication, userInfo.getId(), loginVm.isRememberMe());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(AuthFilter.AUTHORIZATION_HEADER, jwt);
            long expires = TokenProvider.TOKEN_VALIDITY_IN_MILLISECONDS / 1000;
            if (loginVm.isRememberMe()) {
                expires = TokenProvider.TOKEN_VALIDITY_IN_MILLISECONDS_FOR_REMEMBER_ME / 1000;
            }
            Cookie cookie = new Cookie(AuthFilter.AUTHORIZATION_HEADER, jwt);
            cookie.setMaxAge(Integer.parseInt(String.valueOf(expires)));
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            httpHeaders.add("expires", String.valueOf(expires));
            return new ResponseEntity<>(new LoginSuccessVo(jwt, expires, new UserInfo(userInfo.getId(), userInfo.getUsername())), httpHeaders, HttpStatus.OK);
        } catch (DisabledException | LockedException e) {
            throw new BaseException(ResultCode.ACCOUNT_DISABLE);
        } catch (BadCredentialsException e) {
            throw new BaseException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(ResultCode.FAIL);
        }
    }


    @ApiOperation("注册")
    @PostMapping("/register")
    public Result register(@Validated @RequestBody UserRegisterMo mo) {
        // 1. 用户名是否存在
        com.berry.eagleeye.auth.security.dao.entity.UserInfo userInfo = userDaoService.findOneByUsername(mo.getUsername()).orElse(null);
        if (userInfo != null) {
            throw new BaseException(ResultCode.USERNAME_EXIST);
        }
        userInfo = new com.berry.eagleeye.auth.security.dao.entity.UserInfo()
                .setActivated(false)
                .setCreateTime(new Date())
                .setUsername(mo.getUsername())
                .setNickName(mo.getNickName())
                .setEmail(mo.getEmail())
                .setPassword(new BCryptPasswordEncoder().encode(mo.getPassword()));
        if (userDaoService.save(userInfo)) {
            UserAndRole userAndRole = new UserAndRole();
            userAndRole.setUserId(userInfo.getId());
            // 默认普通角色，没有角色管理-。-
            userAndRole.setRoleId(0L);
            userRoleDaoService.save(userAndRole);
        }
        return ResultFactory.wrapper();
    }


    /**
     * Object to return as body in JWT Authentication.
     */
    @Data
    static class LoginSuccessVo {

        private String token;

        private long expires;

        private AuthController.UserInfo userInfo;

        LoginSuccessVo(String token, long expires, AuthController.UserInfo userInfo) {
            this.token = token;
            this.expires = expires;
            this.userInfo = userInfo;
        }

    }

    @Data
    private static class UserInfo {
        private Long id;
        private String username;

        UserInfo(Long id, String username) {
            this.id = id;
            this.username = username;
        }
    }
}
