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
 * @date 2019/11/14 16:24
 * fileName：Oauth2ClientManagementController
 * Use：oauth2 授权客户端管理
 */
@RestController
@RequestMapping("/management/oauth2")
@Api(tags = "oauth2 授权客户端管理")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class Oauth2ClientManagementController {

    @ApiOperation("获取客户端列表")
    @GetMapping(value = "/page_list")
    public Result pageListClient() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("客户端详情")
    @GetMapping(value = "/detail")
    public Result detailClient() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("创建客户端")
    @PostMapping(value = "/create")
    public Result createClient() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("删除客户端")
    @PostMapping(value = "/delete")
    public Result deleteClient() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("修改客户端")
    @PostMapping(value = "/edit")
    public Result editClient() {
        return ResultFactory.wrapper();
    }
}
