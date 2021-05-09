package com.berry.eagleeye.bridge.api;

import com.berry.eagleeye.bridge.remote.IAuthService;
import com.berry.eagleeye.bridge.remote.dto.UserRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @since 2021/3/4
 * fileName：TestAuthFeignClient
 * Use：
 */
@RestController
@RequestMapping("/api/test")
public class TestAuthFeignClient {

    @Autowired
    private IAuthService authService;

    @GetMapping("/getRoleList")
    public void test() {
        Set<UserRoleDTO> listByUserId = authService.findRoleListByUserId(1L);
        System.out.println(listByUserId);
    }

}
