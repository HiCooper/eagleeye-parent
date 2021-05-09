package com.berry.eagleeye.auth.api.management;

import com.berry.eagleeye.auth.common.Result;
import com.berry.eagleeye.auth.common.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/11/12 9:05
 * fileName：UserManagementController
 * Use：用户管理
 */
@RestController
@RequestMapping("/management/user")
@Api(tags = "用户管理")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserManagementController {

    @ApiOperation("分页获取用户列表")
    @GetMapping(value = "/page_list")
    @PreAuthorize("hasPermission('/management/user/page_list', 'read')")
    public Result pageListUser() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("用户详情")
    @GetMapping(value = "/detail")
    public Result detailUser() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("创建用户")
    @PostMapping(value = "/create")
    public Result createUser() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("删除用户")
    @PostMapping(value = "/delete")
    public Result deleteUser() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("编辑用户信息")
    @PostMapping(value = "/edit")
    public Result editUser() {
        return ResultFactory.wrapper();
    }
}
