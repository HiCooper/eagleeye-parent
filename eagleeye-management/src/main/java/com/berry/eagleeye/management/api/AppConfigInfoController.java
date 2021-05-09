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
 * @date 2021-02-13
 * fileName：AppConfigInfoController
 * Use：APP管理
 */
@Api(tags = "APP管理")
@RestController
@RequestMapping("/api/v1/management/app")
public class AppConfigInfoController {

    @ApiOperation("查询APP列表")
    @GetMapping("/list")
    public Result listApp() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("添加APP")
    @PostMapping("/create")
    public Result create() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("移除APP(逻辑移除)")
    @DeleteMapping("/remove")
    public Result remove() {
        return ResultFactory.wrapper();
    }
}
