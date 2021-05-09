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
 * fileName：AtmController
 * Use：自助机管理
 */
@Api(tags = "自助机管理")
@RestController
@RequestMapping("/api/v1/management/atm")
public class AtmController {

    @ApiOperation("分页查询自助机列表")
    @GetMapping("/page_list")
    public Result pageListAtm() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("添加自助机")
    @PostMapping("/create")
    public Result createAtm() {
        return ResultFactory.wrapper();
    }

    @ApiOperation("移除自助机(逻辑移除)")
    @DeleteMapping("/remove")
    public Result removeAtm() {
        return ResultFactory.wrapper();
    }
}
