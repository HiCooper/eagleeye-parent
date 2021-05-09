package com.berry.eagleeye.management.api;

import com.berry.eagleeye.management.common.Result;
import com.berry.eagleeye.management.common.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @since 2021/2/25
 * fileName：SubAccountController
 * Use：
 */
@Api(tags = "子账号管理")
@RestController
@RequestMapping("/api/v1/management/sub_account")
public class SubAccountController {

    @ApiOperation("查询子账号列表")
    @GetMapping("/list")
    public Result pageListSubAccount() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("添加子账号")
    @PostMapping("/create")
    public Result createSubAccount() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("移除子账号(逻辑移除)")
    @DeleteMapping("/remove")
    public Result removeSubAccount() {
        return ResultFactory.wrapper();
    }
}
