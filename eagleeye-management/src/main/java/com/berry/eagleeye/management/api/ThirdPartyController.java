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
 * fileName：ThirdPartyController
 * Use：三方管理
 */
@Api(tags = "三方管理")
@RestController
@RequestMapping("/api/v1/management/third_party")
public class ThirdPartyController {

    @ApiOperation("查询三方列表")
    @GetMapping("/list")
    public Result listThirdParty() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("添加三方")
    @PostMapping("/create")
    public Result createThirdParty() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("移除三方(逻辑移除)")
    @DeleteMapping("/remove")
    public Result removeThirdParty() {
        return ResultFactory.wrapper();
    }
}
