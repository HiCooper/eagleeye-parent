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
 * fileName：ApiDocController
 * Use：
 */
@Api(tags = "API接口文档-管理")
@RestController
@RequestMapping("/api/v1/management/document")
public class ApiDocController {

    @ApiOperation("查询API接口列表")
    @GetMapping("/list")
    public Result pageListApiDocument() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("添加API接口")
    @PostMapping("/create")
    public Result createApiDocument() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("移除API接口(逻辑移除)")
    @DeleteMapping("/remove")
    public Result removeApiDocument() {
        return ResultFactory.wrapper();
    }
}
